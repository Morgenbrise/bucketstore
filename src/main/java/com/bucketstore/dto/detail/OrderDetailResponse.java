package com.bucketstore.dto.detail;

import com.bucketstore.domain.Orders;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "주문 상세 응답 DTO")
@Getter
@Builder
public class OrderDetailResponse {

    @Schema(description = "주문 ID", example = "1")
    private final Long orderId;

    @Schema(description = "주문 코드", example = "ORDER001")
    private final String orderCode;

    @Schema(description = "총 결제 금액", example = "20000")
    private final int totalPrice;

    @Schema(description = "주문 상품 목록")
    private final List<OrderItemResponse> items;

    @Schema(description = "취소된 상품 목록")
    private final List<OrderItemResponse> canceledItems;

    public static OrderDetailResponse from(
            Orders order,
            int totalItemPrice,
            List<OrderItemResponse> itemResponses,
            List<OrderItemResponse> canceledItems
    ) {
        return OrderDetailResponse.builder()
                .orderId(order.getOrderId())
                .orderCode(order.getOrderCode())
                .totalPrice(totalItemPrice)
                .items(itemResponses)
                .canceledItems(canceledItems)
                .build();
    }
}



