package com.example.common.OutboxEvent.Service.Implementation;

import com.example.common.Entity.OutboxEventEntity;
import com.example.common.Event.EventFactory;
import com.example.common.Event.OrderCreatedEvent;
import com.example.common.OutboxEvent.Constants;
import com.example.common.OutboxEvent.Service.IOutboxService;


import com.example.common.OutboxEvent.Repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxServiceImpl implements IOutboxService {
    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;


    @Override
    @Scheduled(fixedDelay = 5000) // 5 saniyede bir çalışır
    public void
    publishPendingEvents() {
        List<OutboxEventEntity> events = outboxEventRepository.findBySentFalse();

        for (OutboxEventEntity event : events) {
            try {
                kafkaTemplate.send(event.getEventType(), event.getAggregateId().toString(), event.getPayload());
                event.setSent(true);
                event.setUpdatedAt(Instant.now());
                log.info("Kafka'ya gönderildi: {}", event.getAggregateId());
            } catch (Exception e) {
                log.error("Kafka'ya gönderim hatası: {}", event.getAggregateId(), e);
            }
        }
    }

    @Override
    @Scheduled(cron = "0 0 2 * * *")
    public void cleanOldSentEvents() {
        Instant threshold = Instant.now().minus(1, ChronoUnit.DAYS); // 1 günden eski gönderilenler silinsin
        int deleted = outboxEventRepository.deleteBySentTrueAndCreatedAtBefore(threshold);
        log.info("Outbox cleanup: {} kayıt silindi", deleted);
    }



}
