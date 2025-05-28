package com.example.notificationservice;

import com.example.common.Event.StockAvailableEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockNotifyConsumer {
    private final ObjectMapper objectMapper;


    @KafkaListener(topics = "stock-notify", groupId = "notification-service")
    public void consume(String message) {
        try {
            StockAvailableEvent event = objectMapper.readValue(message, StockAvailableEvent.class);
            log.info("📦 Stok geldi bildirimi alındı → Ürün: {}, Tarih: {}", event.getProductId(), event.getAvailableAt());
            // Burada e-posta, sms, webhook vs. işlemleri yapılabilir
        } catch (JsonProcessingException e) {
            log.error("StockAvailableEvent parse edilemedi", e);
        }
    }
}
