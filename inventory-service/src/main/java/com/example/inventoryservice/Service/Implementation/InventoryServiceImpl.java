package com.example.inventoryservice.Service.Implementation;

import com.example.common.Entity.OutboxEventEntity;
import com.example.common.Event.EventFactory;
import com.example.common.Event.OrderCreatedEvent;
import com.example.common.Event.OrderValidatedEvent;
import com.example.common.Event.StockAvailableEvent;
import com.example.common.Exception.BusinessException;
import com.example.common.Exception.ErrorCode;
import com.example.common.OutboxEvent.Constants;
import com.example.common.OutboxEvent.Repository.OutboxEventRepository;
import com.example.common.OutboxEvent.Service.Implementation.OutboxServiceImpl;
import com.example.inventoryservice.DTO.Request.InventoryRequest;
import com.example.inventoryservice.DTO.Response.InventoryResponse;
import com.example.inventoryservice.Entity.InventoryItemEntity;
import com.example.inventoryservice.Entity.ReservationEntity;
import com.example.inventoryservice.Repository.InventoryRepository;
import com.example.inventoryservice.Repository.ReservationRepository;
import com.example.inventoryservice.Service.InventoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final OutboxEventRepository outBoxRepository;
    private final ObjectMapper objectMapper;
    private final ReservationRepository reservationRepository;
    private final OutboxServiceImpl outboxService;


    @Override
    @Transactional(readOnly = true)
    public InventoryResponse checkStock(InventoryRequest request) {
        InventoryItemEntity item = inventoryRepository.findByProductId(request.getProductId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));

        boolean inStock = item.getStockQuantity() >= request.getQuantity();
        InventoryResponse response = InventoryResponse.builder().
                productId(item.getProductId())
                .inStock(inStock)
                .build();
        return response;
    }

    @Override
    @Transactional
    public void decreaseStock(InventoryRequest request) {
        InventoryItemEntity item = inventoryRepository.findByProductId(request.getProductId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));

        if (item.getStockQuantity() < request.getQuantity() || item.getReservedQuantity() < request.getQuantity()) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK);

        }

        item.setStockQuantity(item.getStockQuantity() - request.getQuantity());
        item.setReservedQuantity(item.getReservedQuantity() - request.getQuantity());

        log.info("Stok azaltıldı: Ürün={}, Miktar={}, Yeni Stok={}, Yeni Rezerve={}",
                request.getProductId(), request.getQuantity(), item.getStockQuantity(), item.getReservedQuantity());
    }

    @Transactional
    public void updateStock(UUID productId, int quantityToAdd) {
        InventoryItemEntity item = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));

        int oldQuantity = item.getStockQuantity();
        int newQuantity = oldQuantity + quantityToAdd;
        item.setStockQuantity(newQuantity);

        if (oldQuantity == 0 && newQuantity > 0) {
            sendStockAvailableEvent(productId);
        }

        log.info("Stok güncellendi: {} → {}", oldQuantity, newQuantity);
    }

    @Override
    @Transactional
    public void reserveStock(OrderCreatedEvent event) {
        for (OrderCreatedEvent.Item item : event.getItems()) {
            UUID productId = item.getProductId();
            int requestedQuantity = item.getQuantity();

            OrderValidatedEvent orderValidatedEvent=new OrderValidatedEvent(
                    event.getOrderId(),
                    productId,
                    requestedQuantity,
                    event.getCustomerId()
            );

            InventoryItemEntity inventoryItem = inventoryRepository.findByProductIdForUpdate(productId)
                    .orElseThrow(() -> {
                        // Create and save the event
                        EventFactory.createOutboxEvent(Constants.AggregateType.ORDER, Constants.EventType.ORDER_FAILED, String.valueOf(event.getOrderId()),orderValidatedEvent);
                        // Throw the exception
                        return new BusinessException(ErrorCode.STOCK_NOT_FOUND);
                    });
            // Mevcut stoktan rezerve edilmiş miktarı düş
            int availableStock = inventoryItem.getStockQuantity() - inventoryItem.getReservedQuantity();

            if (availableStock < requestedQuantity) {
                // Create and save the event
                EventFactory.createOutboxEvent(Constants.AggregateType.ORDER, Constants.EventType.ORDER_FAILED, String.valueOf(event.getOrderId()),orderValidatedEvent);
                log.error("Yetersiz stok: OrderId={}, ProductId={}, İstenen Miktar={}, Mevcut Stok={}",
                        event.getOrderId(), productId, requestedQuantity, availableStock);
                // Throw the exception
                throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK);
            }

            // Rezerve et
            inventoryItem.setReservedQuantity(inventoryItem.getReservedQuantity() + requestedQuantity);
            inventoryRepository.save(inventoryItem);

            // ReservationEntity oluştur
            ReservationEntity reservation = ReservationEntity.builder()
                    .orderId(event.getOrderId())
                    .productId(productId)
                    .reservedQuantity(requestedQuantity)
                    .expiresAt(Instant.now().plus(Duration.ofMinutes(15))) // örneğin 15 dk sonra expire olsun
                    .build();

            reservationRepository.save(reservation);

            // Outbox'a stok rezerve edildi olayını yaz
            EventFactory.createOutboxEvent(Constants.AggregateType.ORDER, Constants.EventType.ORDER_VALIDATED, String.valueOf(event.getOrderId()),orderValidatedEvent);

            log.info("Stok rezerve edildi: OrderId={}, ProductId={}, Miktar={}",
                    event.getOrderId(), productId, requestedQuantity);
        }
    }

    @Scheduled(fixedRate = 60000) // her 1 dakikada bir çalışır
    @Transactional
    public void releaseExpiredReservations() {
        Instant fifteenMinutessAgo = Instant.now().minus(Duration.ofMinutes(15));
        List<ReservationEntity> expiredReservations = reservationRepository.findByExpiresAtBefore(fifteenMinutessAgo);

        for (ReservationEntity reservation : expiredReservations) {
            try {
                InventoryItemEntity item = inventoryRepository
                        .findByProductIdForUpdate(reservation.getProductId())
                        .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));

                int currentReserved = item.getReservedQuantity();
                item.setReservedQuantity(Math.max(0, currentReserved - reservation.getReservedQuantity()));

                reservationRepository.delete(reservation);
                inventoryRepository.save(item);

                log.info("⚠️ Rezervasyon süresi doldu, ürün serbest bırakıldı: productId={}, quantity={}",
                        reservation.getProductId(), reservation.getReservedQuantity());
            } catch (Exception ex) {
                log.error("‼️ Rezervasyon serbest bırakılırken hata oluştu: reservationId={}", reservation.getId(), ex);
            }
        }
    }




    private void sendStockAvailableEvent(UUID productId) {

        OutboxEventEntity outboxEvent = EventFactory.createOutboxEvent(
                Constants.AggregateType.INVENTORY,
                Constants.EventType.STOCK_AVAILABLE,
                productId.toString(),
                new StockAvailableEvent(productId, Instant.now())
        );

        outBoxRepository.save(outboxEvent);

        log.info("Outbox'a yazıldı: {}", productId);

    }
    @Override
    @KafkaListener(topics = "order_created", groupId = "inventory-service")
    public void handleOrderCreated(String message) throws JsonProcessingException {
        OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);
        reserveStock(event);
    }


}
