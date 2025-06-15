package com.example.orderservice.Service;

import com.example.orderservice.DTO.Request.OrderRequest;
import com.example.orderservice.DTO.Response.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface IOrderService {

     OrderResponse placeOrder(OrderRequest request);
     void cancelOrder(UUID orderId);
     void completeOrder(UUID orderId);
}
