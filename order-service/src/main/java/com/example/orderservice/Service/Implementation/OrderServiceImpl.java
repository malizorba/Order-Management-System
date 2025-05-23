package com.example.orderservice.Service.Implementation;

import com.example.orderservice.DTO.Request.OrderRequest;
import com.example.orderservice.DTO.Response.OrderResponse;
import com.example.orderservice.Entity.OrderEntity;
import com.example.orderservice.Entity.OrderStatus;
import com.example.orderservice.Entity.OutboxEventEntity;
import com.example.orderservice.Repository.OrderRepository;
import com.example.orderservice.Repository.OutBoxRepository;
import com.example.orderservice.Service.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.example.Exception.BusinessException;
import com.example.Exception.ErrorCode;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OutBoxRepository outBoxRepository;
    private final ObjectMapper objectMapper;

    public OrderResponse placeOrder(OrderRequest request) {


        OrderEntity order = OrderEntity.builder()
                .customerId(request.getCustomerId())
                .totalAmount(request.getTotalAmount())
                .orderStatus(OrderStatus.PENDING)
                .build();

        orderRepository.save(order);

        try {
            String payload = objectMapper.writeValueAsString(order);

            OutboxEventEntity event = OutboxEventEntity.builder()
                    .aggregateId(String.valueOf(order.getId()))
                    .aggregateType("Order")
                    .eventType("OrderCreated")
                    .payload(payload)
                    .sent(false)
                    .build();

            outBoxRepository.save(event);

        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR);
        }

        return OrderResponse.builder()
                .orderId(String.valueOf(order.getId()))
                .status(order.getOrderStatus().name())
                .build();
    }

}
