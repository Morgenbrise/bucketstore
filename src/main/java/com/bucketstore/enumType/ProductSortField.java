package com.bucketstore.enumType;

import com.bucketstore.inter.SortField;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@Schema(description = "상품 정렬 필드 코드", example = "PRICE")
public enum ProductSortField implements SortField {

    CREATED("CREATED", "등록일", "createdAt"),
    NAME("NAME", "상품명", "productName"),
    PRICE("PRICE", "가격", "basePrice"),
    BRAND("BRAND", "브랜드", "brand");

    private final String code;
    private final String label;
    private final String fieldName;

    ProductSortField(String code, String label, String fieldName) {
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

    public static ProductSortField from(String code) {
        if (code == null) return CREATED;
        return Arrays.stream(values())
                .filter(f -> f.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(CREATED);
    }
}
