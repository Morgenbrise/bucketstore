package com.bucketstore.enums;

import com.bucketstore.ports.DisplayableCode;

import java.util.Arrays;

public enum OrderStatus implements DisplayableCode {
    PENDING("PENDING", "주문대기"),
    CONFIRMED("CONFIRMED", "결제완료"),
    SHIPPED("SHIPPED", "배송중"),
    DELIVERED("DELIVERED", "배송완료"),
    CANCELLED("CANCELLED", "취소됨");

    private final String code;
    private final String label;

    OrderStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public static OrderStatus from(String code) {
        return Arrays.stream(values())
                .filter(e -> e.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(PENDING);
    }
}
