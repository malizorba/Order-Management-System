package com.example.orderservice.Service;

public interface IOutboxPublisher {

    void publishPendingEvents();
}
