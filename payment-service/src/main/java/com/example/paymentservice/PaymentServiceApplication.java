package com.example.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {
        "com.example.paymentservice",
        "com.example.common"
})

@EntityScan(basePackages = {"package com.example.Entity"})
@EnableJpaRepositories(basePackages = {
        "com.example.common.OutboxEvent.Repository",

})
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}