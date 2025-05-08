package com.order_service.service;

import com.order_service.dto.OrderRequest;
import com.order_service.dto.OrderResponse;
import com.order_service.model.Order;
import com.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .customerEmail(orderRequest.getCustomerEmail())
                .productId(orderRequest.getProductId())
                .price(orderRequest.getPrice())
                .quantity(orderRequest.getQuantity())
                .status(Order.OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);
        OrderResponse response = OrderResponse.fromEntity(savedOrder);

        return response;
    }

}
