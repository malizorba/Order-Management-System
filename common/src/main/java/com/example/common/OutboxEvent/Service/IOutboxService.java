package com.example.common.OutboxEvent.Service;

public interface IOutboxService {

    void publishPendingEvents();

    void cleanOldSentEvents();
}
