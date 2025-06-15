package com.example.orderservice.Listener.Implementation;

import com.example.common.Event.OrderPaymentEvent;
import com.example.common.Event.PaymentFailedEvent;
import com.example.orderservice.Listener.IListenerService;
import com.example.orderservice.Service.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ListenerImplementation implements IListenerService {

    private final ObjectMapper objectMapper;
    private final IOrderService orderService;

    @KafkaListener(topics = "PAYMENT_FAILED", groupId = "order-service")
    public void consume(String message) {
        try {
            PaymentFailedEvent event = objectMapper.readValue(message, PaymentFailedEvent.class);
            log.info("PAYMENT_FAILED eventi alındı: {}", event);

            orderService.cancelOrder(event.getOrderId());

        } catch (Exception e) {
            log.error("PAYMENT_FAILED event işlenirken hata oluştu", e);
        }
    }

    @KafkaListener(topics = "ORDER_PAYMENT", groupId = "order-service")
    public void consumeOrderCompleted(String message) {
        try {
            OrderPaymentEvent event = objectMapper.readValue(message, OrderPaymentEvent.class);
            log.info("ORDER_PAYMENT eventi alındı: {}", event);

            orderService.completeOrder(event.getOrderId());

        } catch (Exception e) {
            log.error("ORDER_PAYMENT event işlenirken hata oluştu", e);
        }
    }
}
