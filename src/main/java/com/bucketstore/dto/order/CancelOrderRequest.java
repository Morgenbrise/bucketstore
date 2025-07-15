package com.bucketstore.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "주문 취소 요청 DTO")
public record CancelOrderRequest(
        @Schema(description = "주문 ID", example = "1")
        Long orderId,

        @Schema(description = "취소할 주문 항목 ID 목록", example = "[1, 2]")
        List<Long> orderItemIds
) {}