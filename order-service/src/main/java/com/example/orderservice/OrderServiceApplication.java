package com.example.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
        "com.example.orderservice",
        "com.example.common"
})
@EnableScheduling
@EntityScan(basePackages = {"package com.example.Entity,package com.example.orderservice.Entity"})
@EnableJpaRepositories(basePackages = {
        "com.example.common.OutboxEvent.Repository",
        "com.example.orderservice.Repository" // kendi servis repo paketlerin
})
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
