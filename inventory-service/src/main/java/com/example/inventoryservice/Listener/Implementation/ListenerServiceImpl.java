package com.example.inventoryservice.Listener.Implementation;

import com.example.common.Event.OrderCreatedEvent;
import com.example.common.Event.OrderValidatedEvent;
import com.example.common.Event.PaymentFailedEvent;
import com.example.inventoryservice.DTO.Request.InventoryRequest;
import com.example.inventoryservice.Listener.IListenerService;
import com.example.inventoryservice.Service.InventoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListenerServiceImpl implements IListenerService {
    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "ORDER_CREATED", groupId = "inventory-service")
    public void handleOrderCreated(String message) throws JsonProcessingException {
        OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);
        inventoryService.reserveStock(event);
    }
    @KafkaListener(topics = "ORDER_PAID", groupId = "inventory-service")
    public void consumeOrderPaid(String message) throws JsonProcessingException {
        try {
            OrderValidatedEvent event = objectMapper.readValue(message, OrderValidatedEvent.class);
            log.info("ORDER_PAID eventi alındı: {}", event);

            InventoryRequest request = InventoryRequest.builder()
                    .productId(event.getProductId())
                    .quantity(event.getQuantity())
                    .build();

            inventoryService.decreaseStock(request);
            log.info("Stok başarıyla azaltıldı. Ürün: {}, Adet: {}", event.getProductId(), event.getQuantity());

        } catch (Exception e) {
            log.error("ORDER_PAID eventi işlenirken hata oluştu", e);
        }
    }
    @KafkaListener(topics = "PAYMENT_FAILED", groupId = "inventory-service")
    public void consume(String message) {
        try {
            PaymentFailedEvent event = objectMapper.readValue(message, PaymentFailedEvent.class);
            log.info("PAYMENT_FAILED eventi alındı: {}", event);

            inventoryService.releaseReservations(event.getOrderId());

        } catch (Exception e) {
            log.error("PAYMENT_FAILED event işlenirken hata oluştu", e);
        }
    }
}
