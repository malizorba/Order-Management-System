package com.example.inventoryservice.Service;

import com.example.common.Event.OrderCreatedEvent;
import com.example.inventoryservice.DTO.Request.InventoryRequest;
import com.example.inventoryservice.DTO.Response.InventoryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface InventoryService {
    InventoryResponse checkStock(InventoryRequest request);

    void decreaseStock(InventoryRequest request);

    void updateStock(UUID productId, int quantityToAdd);

    void reserveStock(OrderCreatedEvent event);

    void releaseExpiredReservations();


    void releaseReservations(UUID orderId);
}
