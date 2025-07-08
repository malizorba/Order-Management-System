package com.example.notificationservice.service.grpc;

import com.example.userproto.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {
    @Bean
    public ManagedChannel userServiceChannel() {
        return ManagedChannelBuilder
                .forAddress("localhost", 9090) // user-service GRPC portu
                .usePlaintext()
                .build();
    }


}
