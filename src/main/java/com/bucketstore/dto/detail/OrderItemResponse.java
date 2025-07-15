package com.bucketstore.dto.detail;

import com.bucketstore.domain.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "주문 상품 응답 DTO")
@Getter
@Builder
public class OrderItemResponse {

    @Schema(description = "상품명")
    private final String productName;

    @Schema(description = "옵션 사이즈")
    private final String size;

    @Schema(description = "수량")
    private final int quantity;

    @Schema(description = "상품 가격 (수량 * 옵션가격 포함)", example = "20000")
    private final int itemPrice;

    @Schema(description = "단가")
    private final int unitPrice;

    @Schema(description = "배송비")
    private final int deliveryFee;

    @Schema(description = "총 금액")
    private final int totalPrice;

    @Schema(description = "주문 상태")
    private final String status;

    public static OrderItemResponse from(OrderItem item) {
        return OrderItemResponse.builder()
                .productName(item.getProduct().getProductName())
                .size(item.getOption().getSize())
                .quantity(item.getQuantity())
                .unitPrice(item.getItemPrice() / item.getQuantity())
                .totalPrice(item.getItemPrice())
                .status(item.getItemStatus().getLabel())
                .build();
    }
}

