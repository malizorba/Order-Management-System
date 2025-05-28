package com.example.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
        "com.example.inventoryservice",
        "com.example.common"
})
@EnableScheduling
@EntityScan(basePackages = {"com.example.common.Entity,com.example.inventoryservice.Entity"})
@EnableJpaRepositories(basePackages = {
        "com.example.common.OutboxEvent.Repository",
        "com.example.inventoryservice.Repository" // kendi servis repo paketlerin
})
public class InventoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
}
