package com.example.orderservice.Service.Implementation;

import com.example.common.Event.EventFactory;
import com.example.common.Event.OrderCreatedEvent;
import com.example.common.OutboxEvent.Constants;
import com.example.common.OutboxEvent.Repository.OutboxEventRepository;
import com.example.orderservice.DTO.Request.OrderRequest;
import com.example.orderservice.DTO.Response.OrderItemResponse;
import com.example.orderservice.DTO.Response.OrderResponse;
import com.example.orderservice.Entity.OrderEntity;
import com.example.orderservice.Entity.OrderItemEntity;
import com.example.orderservice.Entity.OrderStatus;
import com.example.common.Entity.OutboxEventEntity;
import com.example.orderservice.Repository.OrderRepository;
import com.example.orderservice.Service.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.example.common.Exception.BusinessException;
import com.example.common.Exception.ErrorCode;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OutboxEventRepository outBoxRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void cancelOrder(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        order.setOrderStatus(OrderStatus.FAILED);
    }
    @Override
    @Transactional
    public void completeOrder(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        order.setOrderStatus(OrderStatus.COMPLETED);
    }
    @Override
    public OrderResponse placeOrder(OrderRequest request) {


        OrderEntity order = OrderEntity.builder()
                .customerId(request.getCustomerId())
                .orderStatus(OrderStatus.PENDING)
                .build();

        // 2. OrderItem'ları ekle
        List<OrderItemEntity> itemEntities = request.getItems().stream().map(itemReq -> {
            OrderItemEntity item = new OrderItemEntity();
            item.setProductId(itemReq.getProductId());
            item.setQuantity(itemReq.getQuantity());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(itemEntities);

        // 3. Order ve Item'ları kaydet
        orderRepository.save(order);

        // 4. Kafka event'i için payload oluştur
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getCustomerId(),
                order.getItems().stream()
                        .map(item -> new OrderCreatedEvent.Item(
                                item.getProductId(), item.getQuantity()))
                        .collect(Collectors.toList())
        );

        // 5. Outbox tablosuna event'i yaz
        OutboxEventEntity outboxEvent = EventFactory.createOutboxEvent(
                Constants.AggregateType.ORDER,
                Constants.EventType.ORDER_CREATED,
                String.valueOf(order.getId()),
                event
        );
        outBoxRepository.save(outboxEvent);

        // 6. Yanıt döndür
        return OrderResponse.builder()
                .orderId(order.getId())
                .status(order.getOrderStatus().name())
                .items(order.getItems().stream()
                        .map(item -> OrderItemResponse.builder()
                                .productId(item.getProductId())
                                .quantity(item.getQuantity())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

}
