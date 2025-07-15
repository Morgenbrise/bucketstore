package com.bucketstore.controller;

import com.bucketstore.domain.Orders;
import com.bucketstore.dto.order.OrderCreateRequest;
import com.bucketstore.dto.order.OrderResponse;
import com.bucketstore.service.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrdersService ordersService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderCreateRequest request) {
        Orders orders = ordersService.createOrder(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrderResponse.from(orders));
    }
}
