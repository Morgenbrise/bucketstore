package com.bucketstore.dto.detail;

import com.bucketstore.domain.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "주문 상품 응답 DTO")
@Getter
@Builder
public class OrderItemResponse {

    @Schema(description = "상품명", example = "티셔츠")
    private final String productName;

    @Schema(description = "옵션 사이즈", example = "M")
    private final String size;

    @Schema(description = "수량", example = "2")
    private final int quantity;

    @Schema(description = "상품 총 가격", example = "20000")
    private final int itemPrice;

    @Schema(description = "단가", example = "10000")
    private final int unitPrice;

    @Schema(description = "취소 배송비", example = "3000")
    private final int deliveryFee;

    @Schema(description = "총 금액", example = "20000")
    private final int totalPrice;

    @Schema(description = "상태", example = "주문됨")
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

