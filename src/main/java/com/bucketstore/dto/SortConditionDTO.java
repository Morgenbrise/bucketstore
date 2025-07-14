package com.bucketstore.dto;

import com.bucketstore.enumType.ProductSortField;
import com.bucketstore.enumType.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;

public record SortConditionDTO(
        @Schema(description = "정렬 필드", implementation = ProductSortField.class)
        ProductSortField code,

        @Schema(description = "정렬 방향", implementation = SortDirection.class)
        SortDirection direction
) {}