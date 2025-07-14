package com.bucketstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "정렬 조건")
public record SortConditionDTO(
        @Schema(description = "정렬 코드 (예: NAME, PRICE, CREATED)", example = "NAME")
        String code,

        @Schema(description = "정렬 방향 (ASC 또는 DESC)", example = "ASC")
        String direction
) {}