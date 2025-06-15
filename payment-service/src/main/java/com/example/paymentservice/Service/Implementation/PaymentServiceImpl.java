package com.example.paymentservice.Service.Implementation;

import com.example.common.Entity.OutboxEventEntity;
import com.example.common.Event.EventFactory;
import com.example.common.Event.OrderValidatedEvent;
import com.example.common.Event.PaymentFailedEvent;
import com.example.common.OutboxEvent.Constants;
import com.example.common.OutboxEvent.Repository.OutboxEventRepository;
import com.example.paymentservice.Service.IPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements IPaymentService {

    private final OutboxEventRepository outboxEventRepository;

    @Override
    public void processPayment(OrderValidatedEvent event) {
        log.info("Ödeme işleniyor... OrderId: {}", event.getOrderId());

        // 1. Sahte ödeme işlemi
        try {
            Thread.sleep(2000); // Simülasyon
            log.info("Ödeme başarıyla tamamlandı. OrderId: {}", event.getOrderId());

            // 2. ORDER_PAID event oluştur
            OutboxEventEntity outboxEvent = EventFactory.createOutboxEvent(
                    Constants.AggregateType.PAYMENT,
                    Constants.EventType.ORDER_PAID,
                    event.getOrderId().toString(),
                    event // payload olarak aynı event’i kullanabiliriz
            );

            // 3. Outbox'a yaz
            outboxEventRepository.save(outboxEvent);
            log.info("ORDER_PAID event Outbox'a yazıldı: {}", outboxEvent);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();


            OutboxEventEntity failedOutboxEvent = EventFactory.createOutboxEvent(
                    Constants.AggregateType.PAYMENT,
                    Constants.EventType.PAYMENT_FAILED,
                    event.getOrderId().toString(),
                    new PaymentFailedEvent(
                            event.getOrderId(),
                            event.getCustomerId()
                    )
            );

            // 5. Outbox'a yaz
            outboxEventRepository.save(failedOutboxEvent);
            log.info("PAYMENT_FAILED event Outbox'a yazıldı: {}", failedOutboxEvent);

            // 6. Hata fırlat
            throw new RuntimeException("Ödeme işlemi başarısız oldu", e);

        }



    }
}
