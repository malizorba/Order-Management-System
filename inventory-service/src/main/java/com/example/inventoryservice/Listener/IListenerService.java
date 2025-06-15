package com.example.inventoryservice.Listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface IListenerService {
    void handleOrderCreated(String message) throws JsonProcessingException;

    void consumeOrderPaid(String message) throws JsonProcessingException;
}
