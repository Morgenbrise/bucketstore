package com.bucketstore.repository.orderItem;

import com.bucketstore.dto.detail.OrderItemResponse;

import java.util.List;

public interface OrderItemQueryRepository {
    List<OrderItemResponse> findByOrderId(Long orderId);
}
