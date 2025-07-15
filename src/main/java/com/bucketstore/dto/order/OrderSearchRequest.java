package com.bucketstore.dto.order;

import com.bucketstore.common.dto.SortRequestCondition;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "주문 조회 요청")
public record OrderSearchRequest(
        @Schema(description = "시작 인덱스", example = "0")
        int page,
        @Schema(description = "조회 개수", example = "10")
        int size,
        @Schema(description = "정렬 조건 목록")
        List<SortRequestCondition> sort
) {}
