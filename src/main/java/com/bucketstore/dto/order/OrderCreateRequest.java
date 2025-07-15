package com.bucketstore.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "주문 생성 요청 DTO")
public record OrderCreateRequest(
        @Schema(description = "사용자 ID", example = "1")
        Long userId,
        @Schema(description = "주문 상품 리스트")
        List<OrderItemRequest> items,
        @Schema(description = "배송지 정보")
        DeliveryRequest delivery
) {
    @Schema(description = "주문 상품 요청 DTO")
    public record OrderItemRequest(
            @Schema(description = "상품 코드", example = "PROD001") String productCode,
            @Schema(description = "사이즈", example = "M") String size,
            @Schema(description = "수량", example = "2") int quantity) {}

    @Schema(description = "배송지 요청 DTO")
    public record DeliveryRequest(
            @Schema(description = "수령인 이름", example = "홍길동") String receiverName,
            @Schema(description = "연락처", example = "010-1234-5678") String phoneNumber,
            @Schema(description = "주소", example = "서울특별시 강남구 테헤란로") String address,
            @Schema(description = "우편번호", example = "12345") String zipCode,
            @Schema(description = "배송 요청사항", example = "문 앞에 놔주세요") String deliveryMessage
    ) {}
}