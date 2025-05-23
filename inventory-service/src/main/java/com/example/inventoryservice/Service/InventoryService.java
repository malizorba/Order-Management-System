package com.example.inventoryservice.Service;

import com.example.inventoryservice.DTO.Request.InventoryRequest;
import com.example.inventoryservice.DTO.Response.InventoryResponse;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {
    InventoryResponse checkStock(InventoryRequest request);

    void decreaseStock(InventoryRequest request);
}
