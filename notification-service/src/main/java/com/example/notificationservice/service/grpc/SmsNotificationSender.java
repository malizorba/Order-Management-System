package com.example.notificationservice.service.grpc;

import com.example.notificationservice.NotificationChannel;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationSender implements INotificationSender {

    @Override
    public void sendNotification(String recipient, String message) {
        // Burada SMS gönderme işlemi yapılır
        System.out.println("SMS gönderildi: " + recipient + " - " + message);
        // Gerçek SMS gönderme kodu burada yer alabilir
    }

    @Override
    public NotificationChannel getChannelType() {
        return NotificationChannel.SMS;
    }
}
