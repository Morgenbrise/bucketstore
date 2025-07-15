package com.bucketstore.common.enums;

import com.querydsl.core.types.Order;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "정렬 방향", example = "ASC")
public enum SortDirection {

    ASC, DESC;

    public static SortDirection from(String value) {
        if (value == null) return DESC;
        try {
            return SortDirection.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DESC;
        }
    }

    public org.springframework.data.domain.Sort.Direction toSpringDirection() {
        return this == ASC ? org.springframework.data.domain.Sort.Direction.ASC : org.springframework.data.domain.Sort.Direction.DESC;
    }

    public Order toQueryDslDirection() {
        return this == ASC ? Order.ASC : Order.DESC;
    }
}
