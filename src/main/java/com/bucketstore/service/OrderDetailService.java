package com.bucketstore.service;

import com.bucketstore.domain.Orders;
import com.bucketstore.dto.detail.OrderDetailResponse;
import com.bucketstore.dto.detail.OrderItemResponse;
import com.bucketstore.enums.status.OrderItemStatus;
import com.bucketstore.repository.order.OrdersRepository;
import com.bucketstore.repository.orderItem.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderDetailResponse findOrderDetail(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다: " + orderId));

        List<OrderItemResponse> allItems = orderItemRepository.findByOrderId(orderId);

        // 정상/취소 아이템 분리
        List<OrderItemResponse> orderedItems = allItems.stream()
                .filter(item -> item.getStatus().equals(OrderItemStatus.ORDERED.getCode()))
                .toList();

        List<OrderItemResponse> canceledItems = allItems.stream()
                .filter(item -> item.getStatus().equals(OrderItemStatus.CANCELED.getCode()))
                .toList();

        // 정상 주문 금액 합산
        int totalItemPrice = orderedItems.stream()
                .mapToInt(OrderItemResponse::getItemPrice)
                .sum();

        return OrderDetailResponse.builder()
                .orderId(order.getOrderId())
                .orderCode(order.getOrderCode())
                .totalPrice(totalItemPrice)
                .items(orderedItems)
                .canceledItems(canceledItems)
                .build();
    }

}
