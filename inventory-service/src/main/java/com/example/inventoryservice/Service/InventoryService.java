package com.example.inventoryservice.Service;

import com.example.inventoryservice.DTO.Request.InventoryRequest;
import com.example.inventoryservice.DTO.Response.InventoryResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface InventoryService {
    InventoryResponse checkStock(InventoryRequest request);

    void decreaseStock(InventoryRequest request);

    void updateStock(UUID productId, int quantityToAdd);
}
