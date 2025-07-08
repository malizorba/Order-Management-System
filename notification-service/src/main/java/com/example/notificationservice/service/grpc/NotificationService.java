package com.example.notificationservice.service.grpc;

import com.example.notificationservice.NotificationChannel;
import com.example.userproto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
//    private final List<INotificationSender> notificationSenders;
//    private final UserGrpcClient userGrpcClient; // gRPC ile kullanıcı verisi alacağız
//
//    public void notifyUser(String email, String message) {
//        UserResponse user = userGrpcClient.getUserByEmail(email);
//        NotificationChannel channel = NotificationChannel.valueOf(user.getPreferredChannel());
//
//        INotificationSender sender = notificationSenders.stream()
//                .filter(s -> s.getChannelType() == channel)
//                .findFirst()
//                .orElseThrow(() -> new IllegalStateException("Uygun kanal bulunamadı"));
//
//        sender.sendNotification(email, message);
//    }
}
