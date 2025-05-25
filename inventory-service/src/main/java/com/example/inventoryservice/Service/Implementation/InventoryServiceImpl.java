package com.example.inventoryservice.Service.Implementation;

import com.example.common.Entity.OutboxEventEntity;
import com.example.common.Event.EventFactory;
import com.example.common.Event.StockAvailableEvent;
import com.example.common.Exception.BusinessException;
import com.example.common.Exception.ErrorCode;
import com.example.common.OutboxEvent.Constants;
import com.example.common.OutboxEvent.Repository.OutboxEventRepository;
import com.example.inventoryservice.DTO.Request.InventoryRequest;
import com.example.inventoryservice.DTO.Response.InventoryResponse;
import com.example.inventoryservice.Entity.InventoryItemEntity;
import com.example.inventoryservice.Repository.InventoryRepository;
import com.example.inventoryservice.Service.InventoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final OutboxEventRepository outBoxRepository;
    private final ObjectMapper objectMapper;


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

        if (item.getStockQuantity() < request.getQuantity()) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK);

        }
        int oldQuantity = item.getStockQuantity();
        item.setStockQuantity(oldQuantity - request.getQuantity());
        log.info("Stok azaltıldı: Ürün={}, Eski={}, Yeni={}", request.getProductId(), oldQuantity, item.getStockQuantity());

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

}
