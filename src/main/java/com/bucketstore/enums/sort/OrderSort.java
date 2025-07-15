package com.bucketstore.enums.sort;

import com.bucketstore.common.enums.SortDirection;
import com.bucketstore.domain.Orders;
import com.bucketstore.ports.SortableField;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@Schema(description = "주문 정렬 필드 코드")
@AllArgsConstructor
public enum OrderSort implements SortableField {

    CREATED("CREATED", "주문일시", "orderDate"),
    STATUS("STATUS", "주문상태", "orderStatus"),
    PRICE("PRICE", "총 결제금액", "totalPrice");

    private final String code;
    private final String label;
    private final String fieldName;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public Sort.Order toOrder(SortDirection direction) {
        return new Sort.Order(direction.toSpringDirection(), fieldName);
    }

    public static OrderSort from(String code) {
        if (code == null) return CREATED;
        return Arrays.stream(values())
                .filter(f -> f.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(CREATED);
    }

    @Override
    public OrderSpecifier<?> toOrderSpecifier(SortDirection direction) {
        PathBuilder<Orders> path = new PathBuilder<>(Orders.class, "orders");
        if(this == CREATED) {
            return new OrderSpecifier<>(direction.toQueryDslDirection(), path.getDateTime(CREATED.fieldName, java.time.LocalDateTime.class));
        }

        if(this == STATUS) {
            return new OrderSpecifier<>(direction.toQueryDslDirection(), path.getString(STATUS.fieldName));
        }

        if(this == PRICE) {
            return new OrderSpecifier<>(direction.toQueryDslDirection(), path.getNumber(PRICE.fieldName, Integer.class));
        }

        throw new IllegalArgumentException("Unsupported field: " + this);
    }

}
