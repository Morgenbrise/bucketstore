package com.bucketstore.dto.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemRaw {
    private final String productName;
    private final String size;
    private final int quantity;
    private final int itemPrice;
    private final int deliveryFee;
    private final String status;
}
