package com.order_service.service;

import com.order_service.dto.OrderRequest;
import com.order_service.dto.OrderResponse;
import com.order_service.model.Order;
import com.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {

        try {

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

        } catch (Exception e) {
            throw new RuntimeException("Failed to create order: " + e.getMessage(), e);
        }

    }

    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll().stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());

    }
}
