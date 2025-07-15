package com.bucketstore.common.dto;

import com.bucketstore.enums.ProductDisplayableCode;
import com.bucketstore.enums.SortDirection;
import com.bucketstore.ports.SortableField;
import io.swagger.v3.oas.annotations.media.Schema;

public record SortConditionDTO(
        @Schema(description = "정렬 필드", example = "PRICE", implementation = SortableField.class)
        ProductDisplayableCode code,

        @Schema(description = "정렬 방향", example = "ASC", implementation = SortDirection.class)
        SortDirection direction
) {}