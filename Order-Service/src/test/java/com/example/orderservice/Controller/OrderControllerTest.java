package com.example.orderservice.Controller;

import com.example.common.OutboxEvent.Repository.OutboxEventRepository;
import com.example.orderservice.DTO.Request.OrderItemRequest;
import com.example.orderservice.DTO.Request.OrderRequest;
import com.example.orderservice.DTO.Response.OrderResponse;
import com.example.orderservice.Repository.OrderRepository;
import com.example.orderservice.Service.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ContextConfiguration(classes = {OrderController.class})
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IOrderService orderService;



    @Test
    void createOrder_ShouldReturnOrderResponse() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setCustomerId("123");

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setQuantity(2);
        itemRequest.setProductId(UUID.randomUUID());

        request.setItems(new ArrayList<>());
        request.getItems().add(itemRequest);

        OrderResponse response = new OrderResponse();
        response.setCustomerId("123");
        response.setOrderId(UUID.randomUUID());
        response.setStatus("PENDING");

        // service mock
        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(response);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("123"))
                .andExpect(jsonPath("$.status").value("PENDING"));
        System.out.println("Test passed successfully");
        System.out.println(jsonPath("$.customerId").value("123"));
    }
}
