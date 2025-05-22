package com.example.orderservice.Service.Implementation;

import com.example.orderservice.Entity.OutboxEventEntity;
import com.example.orderservice.Repository.OutBoxRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisherImpl {
    private final OutBoxRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 5000) // 5 saniyede bir çalışır
    @Transactional
    public void publishPendingEvents() {
        List<OutboxEventEntity> events = outboxEventRepository.findBySentFalse();

        for (OutboxEventEntity event : events) {
            try {
                kafkaTemplate.send(event.getAggregateType(), event.getAggregateId().toString(), event.getPayload());
                event.setSent(true);
                event.setUpdatedAt(Instant.now());
                log.info("Kafka'ya gönderildi: {}", event.getAggregateId());
            } catch (Exception e) {
                log.error("Kafka'ya gönderim hatası: {}", event.getAggregateId(), e);
            }
        }
    }
}
