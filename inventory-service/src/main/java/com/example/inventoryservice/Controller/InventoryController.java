package com.example.inventoryservice.Controller;


import com.example.inventoryservice.DTO.Request.InventoryRequest;
import com.example.inventoryservice.DTO.Response.InventoryResponse;
import com.example.inventoryservice.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/check")
    public ResponseEntity<InventoryResponse> checkStock(@RequestBody InventoryRequest request) {
        InventoryResponse response = inventoryService.checkStock(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/decrease")
    public ResponseEntity<Void> decreaseStock(@RequestBody InventoryRequest request) {
        inventoryService.decreaseStock(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/update")
    public ResponseEntity<Void> updateStock(
            @RequestParam UUID productId,
            @RequestParam int quantityToAdd
    ) {
        inventoryService.updateStock(productId, quantityToAdd);
        return ResponseEntity.ok().build();
    }
}
