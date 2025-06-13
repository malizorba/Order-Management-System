package com.example.common.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderValidatedEvent {
    private UUID orderId;
    private UUID productId;
    private int quantity;
    private String customerId;

}
