package com.example.paymentservice.Service;

import com.example.common.Event.OrderValidatedEvent;
import org.springframework.stereotype.Service;

@Service
public interface IPaymentService {

    void processPayment(OrderValidatedEvent event);
}
