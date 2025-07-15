package com.bucketstore.dto.order;

import com.bucketstore.domain.Orders;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Schema(description = "주문 응답 DTO")
public class OrderResponse {

    private String orderCode;
    private String orderStatus;
    private Integer totalPrice;
    private Integer deliveryFee;
    private LocalDateTime orderDate;

    public static OrderResponse from(Orders order) {
        return OrderResponse.builder()
                .orderCode(order.getOrderCode())
                .orderStatus(order.getOrderStatus().name())
                .totalPrice(order.getTotalPrice())
                .deliveryFee(order.getDeliveryFee())
                .orderDate(order.getOrderDate())
                .build();
    }
}