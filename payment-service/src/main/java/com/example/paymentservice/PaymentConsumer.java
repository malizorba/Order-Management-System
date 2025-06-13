package com.example.paymentservice;

import com.example.common.Event.OrderValidatedEvent;
import com.example.paymentservice.Service.IPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final ObjectMapper objectMapper;
    private final IPaymentService paymentService;

    /**
     * ORDER_VALIDATED event'ini dinler ve ödeme işlemini başlatır.
     * Bu event, siparişin doğrulandığını ve ödeme işleminin yapılabileceğini belirtir.
     *
     * @param message ORDER_VALIDATED event mesajı
     */

    @KafkaListener(topics = "ORDER_VALIDATED", groupId = "payment-service")
    public void consumeValidatedOrder(String message) {
        try {
            OrderValidatedEvent event = objectMapper.readValue(message, OrderValidatedEvent.class);
            log.info("ORDER_VALIDATED eventi alındı: {}", event);

            // ödeme işlemini başlat

            log.info("Ödeme başlatılıyor. OrderId: {}, CustomerId: {}", event.getOrderId(), event.getCustomerId());
            paymentService.processPayment(event);



        } catch (Exception e) {
            log.error("ORDER_VALIDATED mesajı işlenirken hata oluştu", e);
        }
    }
}

