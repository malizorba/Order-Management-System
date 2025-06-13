package com.example.common.Event;

import com.example.common.Entity.OutboxEventEntity;
import com.example.common.OutboxEvent.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventFactory {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static OutboxEventEntity createOutboxEvent(
            Constants.AggregateType aggregateType,
            Constants.EventType eventType,
            String aggregateId,
            Object payloadObj
    ) {
        try {
            String payload = objectMapper.writeValueAsString(payloadObj);
            return OutboxEventEntity.builder()
                    .aggregateId(aggregateId)
                    .aggregateType(String.valueOf(aggregateType))
                    .eventType(String.valueOf(eventType))
                    .payload(payload)
                    .sent(false)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Payload JSON dönüşüm hatası", e);
        }
    }

}
