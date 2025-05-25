package com.example.orderservice.Service.Implementation;

import com.example.common.Event.EventFactory;
import com.example.common.OutboxEvent.Constants;
import com.example.common.OutboxEvent.Repository.OutboxEventRepository;
import com.example.orderservice.DTO.Request.OrderRequest;
import com.example.orderservice.DTO.Response.OrderResponse;
import com.example.orderservice.Entity.OrderEntity;
import com.example.orderservice.Entity.OrderStatus;
import com.example.common.Entity.OutboxEventEntity;
import com.example.orderservice.Repository.OrderRepository;
import com.example.orderservice.Service.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.example.common.Exception.BusinessException;
import com.example.common.Exception.ErrorCode;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OutboxEventRepository outBoxRepository;
    private final ObjectMapper objectMapper;

    public OrderResponse placeOrder(OrderRequest request) {


        OrderEntity order = OrderEntity.builder()
                .customerId(request.getCustomerId())
                .totalAmount(request.getTotalAmount())
                .orderStatus(OrderStatus.PENDING)
                .build();

        orderRepository.save(order);

        OutboxEventEntity event = EventFactory.createOutboxEvent(
                Constants.AggregateType.ORDER,
                Constants.EventType.ORDER_CREATED,
                String.valueOf(order.getId()),
                order
        );

            outBoxRepository.save(event);


        return OrderResponse.builder()
                .orderId(String.valueOf(order.getId()))
                .status(order.getOrderStatus().name())
                .build();
    }

}
