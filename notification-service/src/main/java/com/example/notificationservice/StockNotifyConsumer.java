package com.example.notificationservice;

import com.example.common.Event.StockAvailableEvent;
import com.example.notificationservice.service.grpc.EmailNotificationSender;
import com.example.notificationservice.service.grpc.SmsNotificationSender;
import com.example.userproto.FavoriteProductRequest;
import com.example.userproto.UserListResponse;
import com.example.userproto.UserResponse;
import com.example.userproto.UserServiceGrpc;
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

    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;
    private final EmailNotificationSender emailNotificationSender;
    private final SmsNotificationSender smsNotificationSender;

    @KafkaListener(topics = "stock-notify", groupId = "notification-service")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            String payload = record.value();
            StockAvailableEvent event = objectMapper.readValue(payload, StockAvailableEvent.class);

            log.info("StockAvailableEvent alındı: {}", event);
            FavoriteProductRequest request = FavoriteProductRequest.newBuilder()
                    .setProductId(String.valueOf(event.getProductId()))
                    .build();


            // Kullanıcı bilgilerini gRPC ile çek
            UserListResponse response = userServiceBlockingStub.getUsersByFavoriteProduct(request);

            // Bildirim gönder (örnek amaçlı logluyoruz)
            for (UserResponse user :response.getUsersList()){
                switch(user.getPreferredChannel()){
                    case EMAIL:
                        emailNotificationSender.sendNotification(user.getEmail(),"Favorite Product is in stock");
                        break;
                    case SMS:
                        smsNotificationSender.sendNotification(user.getPhoneNumber(),"Favorite Product is in stock");
                        break;
                }
            }
        } catch (Exception e) {
            log.error("StockNotifyConsumer içinde hata: ", e);
        }
    }
}