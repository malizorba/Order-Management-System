package com.example.notificationservice.service.grpc;

import com.example.notificationservice.NotificationChannel;
import org.springframework.stereotype.Service;

@Service
public interface INotificationSender {
    void sendNotification(String recipient, String message);
    NotificationChannel getChannelType(); // EMAIL veya SMS
}
