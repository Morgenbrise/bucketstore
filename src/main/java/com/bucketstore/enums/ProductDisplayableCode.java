package com.bucketstore.enums;

import com.bucketstore.domain.Product;
import com.bucketstore.domain.QProduct;
import com.bucketstore.ports.DisplayableCode;
import com.bucketstore.ports.SortableField;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Schema(description = "상품 정렬 필드 코드", example = "PRICE")
public enum ProductDisplayableCode implements SortableField {

    CREATED("CREATED", "등록일", "createdAt"),
    NAME("NAME", "상품명", "productName"),
    PRICE("PRICE", "가격", "basePrice");

    private final String code;
    private final String label;
    private final String fieldName;

    ProductDisplayableCode(String code, String label, String fieldName) {
        this.code = code;
        this.label = label;
        this.fieldName = fieldName;
    }

    @Override
    public Sort.Order toOrder(SortDirection direction) {
        return new Sort.Order(direction.toSpringDirection(), fieldName);
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public static ProductDisplayableCode from(String code) {
        if (code == null) return CREATED;
        return Arrays.stream(values())
                .filter(f -> f.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(CREATED);
    }

    @Override
    public OrderSpecifier<?> toOrderSpecifier(SortDirection direction) {
        PathBuilder<Product> path = new PathBuilder<>(Product.class, "product");
        if (this == CREATED) {
            return new OrderSpecifier<>(direction.toQueryDslDirection(), path.getDateTime(CREATED.fieldName, java.time.LocalDateTime.class));
        }

        if (this == NAME) {
            return new OrderSpecifier<>(direction.toQueryDslDirection(), path.getString(NAME.fieldName));
        }

        if (this == PRICE) {
            return new OrderSpecifier<>(direction.toQueryDslDirection(), path.getNumber(PRICE.fieldName, Integer.class));
        }

        throw new IllegalArgumentException("Unsupported field: " + this);
    }
}
