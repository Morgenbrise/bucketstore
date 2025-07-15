package com.bucketstore.repository.order;

import com.bucketstore.dto.order.OrderResponse;
import com.bucketstore.dto.order.OrderSearchRequest;

import java.util.List;

public interface OrdersQueryRepository {
    List<OrderResponse> findOrders(OrderSearchRequest request);
}
