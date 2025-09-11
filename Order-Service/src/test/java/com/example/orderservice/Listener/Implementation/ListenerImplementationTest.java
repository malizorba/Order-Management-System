package com.example.orderservice.Listener.Implementation;

import com.example.common.Event.OrderPaymentEvent;
import com.example.common.Event.PaymentFailedEvent;
import com.example.orderservice.Service.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ListenerImplementationTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private IOrderService orderService;
    @InjectMocks
    private ListenerImplementation listenerImplementation;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void consume_paymentFailedEvent_success() throws Exception {
        String message = "{\"orderId\":\"123e4567-e89b-12d3-a456-426614174000\"}";

        // Burada PaymentFailedEvent sınıfını kullanarak bir mock nesne oluşturabilirsiniz
         PaymentFailedEvent event = new PaymentFailedEvent(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),"123");

        // ObjectMapper'ın readValue metodunu mock'layın
         Mockito.when(objectMapper.readValue(message, PaymentFailedEvent.class)).thenReturn(event);

        // orderService.cancelOrder metodunun çağrıldığını doğrulayın
         listenerImplementation.consume(message);
         Mockito.verify(orderService).cancelOrder(event.getOrderId());

    }
    @Test
    void consume_orderPaymentEvent_success() throws Exception {
        String message = "{\"orderId\":\"123e4567-e89b-12d3-a456-426614174000\"}";
        OrderPaymentEvent event = new OrderPaymentEvent(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),"123");

        Mockito.when(objectMapper.readValue(message, OrderPaymentEvent.class)).thenReturn(event);

        listenerImplementation.consumeOrderCompleted(message);
        Mockito.verify(orderService,Mockito.times(1)).completeOrder(event.getOrderId());
    }

    @Test
    void consume_invalidJson_shouldNotCrash() {
        String invalidMessage="invalid-json";

        listenerImplementation.consume(invalidMessage);

        Mockito.verifyNoInteractions(orderService);
    }
}