package com.example.notificationservice;

import com.example.common.Event.StockAvailableEvent;
import com.example.userproto.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockNotifyConsumer {

    private final ObjectMapper objectMapper;
    private final UserGrpcClient grpcUserClient;

    @KafkaListener(topics = "stock-notify", groupId = "notification-service")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            String payload = record.value();
            StockAvailableEvent event = objectMapper.readValue(payload, StockAvailableEvent.class);

            log.info("StockAvailableEvent alındı: {}", event);

            // Kullanıcı bilgilerini gRPC ile çek
            UserResponse user = grpcUserClient.getUserByEmail(event.getCustomerEmail());

            // Bildirim gönder (örnek amaçlı logluyoruz)
            log.info("Kullanıcıya bildirim gönderiliyor - Ad: {}, Email: {}, Telefon: {}",
                    user.getFullName(), user.getEmail(), user.getPhoneNumber());

        } catch (Exception e) {
            log.error("StockNotifyConsumer içinde hata: ", e);
        }
    }
}