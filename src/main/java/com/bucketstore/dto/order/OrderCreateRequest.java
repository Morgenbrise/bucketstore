package com.bucketstore.dto.order;

import java.util.List;

public record OrderCreateRequest(
        Long userId,
        List<OrderItemRequest> items,
        DeliveryRequest delivery
) {
    public record OrderItemRequest(String productCode, String size, int quantity) {}
    public record DeliveryRequest(String receiverName, String phoneNumber, String address, String zipCode, String deliveryMessage) {}
}