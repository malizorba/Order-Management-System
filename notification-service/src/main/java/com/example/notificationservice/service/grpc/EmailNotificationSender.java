package com.example.notificationservice.service.grpc;

import com.example.notificationservice.NotificationChannel;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationSender implements INotificationSender{
    @Override
    public void sendNotification(String recipient, String message) {
        // Burada e-posta gönderme işlemi yapılır
        System.out.println("E-posta gönderildi: " + recipient + " - " + message);
        // Gerçek e-posta gönderme kodu burada yer alabilir
    }

    @Override
    public NotificationChannel getChannelType() {
        return NotificationChannel.EMAIL;
    }
}
