package com.example.orderservice.Service.Implementation;

import com.example.common.Exception.BusinessException;
import com.example.common.OutboxEvent.Repository.OutboxEventRepository;
import com.example.common.OutboxEvent.Service.Implementation.OutboxServiceImpl;
import com.example.orderservice.DTO.Request.OrderItemRequest;
import com.example.orderservice.DTO.Request.OrderRequest;
import com.example.orderservice.DTO.Response.OrderResponse;
import com.example.orderservice.Entity.OrderEntity;
import com.example.orderservice.Entity.OrderItemEntity;
import com.example.orderservice.Entity.OrderStatus;
import com.example.orderservice.Repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
//@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    /* @Mock
    private OrderRepository orderRepository;

    @Mock
    private OutboxEventRepository outBoxRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderServiceImpl orderService;*/

    private OrderRepository orderRepository;
    private OutboxServiceImpl outboxService;
    private OrderServiceImpl orderService;
    private OutboxEventRepository outboxEventRepository;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        outboxEventRepository = mock(OutboxEventRepository.class);
        objectMapper = mock(ObjectMapper.class);

        orderService = new OrderServiceImpl(orderRepository, outboxEventRepository, objectMapper);
    }
    @Test
    void cancelOrder_orderNotFound() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            orderService.cancelOrder(orderId);
        });

        assertEquals("ORDER_NOT_FOUND", exception.getErrorCode().name());
    }


    @Test
    void cancelOrder_success() {
        UUID orderId = UUID.randomUUID();
        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        order.setOrderStatus(OrderStatus.PENDING);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        orderService.cancelOrder(orderId);

        // Assert
        assertEquals(OrderStatus.FAILED, order.getOrderStatus());
    }

    @Test
    void completeOrder_success() {
        OrderEntity orderEntity=new OrderEntity();
        orderEntity.setOrderStatus(OrderStatus.PENDING);
        orderEntity.setId(UUID.randomUUID());

        when(orderRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(orderEntity));

        orderService.completeOrder(orderEntity.getId());

        assertEquals(OrderStatus.COMPLETED,orderEntity.getOrderStatus());
    }
    @Test
    void completeOrder_notFound(){
        UUID orderId=UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class,()->orderService.completeOrder(orderId));
    }

    @Test
    void placeOrder() {
        OrderEntity orderEntity=new OrderEntity();
        orderEntity.setOrderStatus(OrderStatus.PENDING);
        orderEntity.setCustomerId("123");

        OrderItemEntity item1=new OrderItemEntity();
        item1.setProductId(UUID.randomUUID());
        item1.setQuantity(2);
        item1.setOrder(orderEntity);
        orderEntity.getItems().add(item1);

        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(i->{
            OrderEntity saved=i.getArgument(0);
            saved.setId(UUID.randomUUID());
            saved.getItems().forEach(it->it.setId(UUID.randomUUID()));
            saved.setOrderStatus(OrderStatus.PENDING);
            return saved;
        });
        OrderRequest request = new OrderRequest();
        request.setCustomerId(orderEntity.getCustomerId());

        List<OrderItemRequest> itemRequests = new ArrayList<>();

            OrderItemRequest item = new OrderItemRequest();
            item.setProductId(item1.getProductId());
            item.setQuantity(item1.getQuantity());
            itemRequests.add(item);


        request.setItems(itemRequests);


        OrderResponse response =orderService.placeOrder(request);
        assertNotNull(response.getOrderId());
        assertEquals(OrderStatus.PENDING.name(),response.getStatus());
        assertNotNull(response.getItems().get(0).getProductId());

        verify(outboxEventRepository,times(1)).save(argThat(outbox -> outbox.getAggregateType().equals("ORDER") &&
                outbox.getEventType().equals("ORDER_CREATED") &&
                outbox.getAggregateId().equals(response.getOrderId().toString())
        ));

    }
}