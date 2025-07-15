package com.bucketstore.enums.status;

import com.bucketstore.ports.DisplayableCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

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

    @Converter(autoApply = false)
    public static class ConverterImpl implements AttributeConverter<OrderStatus, String> {

        @Override
        public String convertToDatabaseColumn(OrderStatus attribute) {
            return attribute != null ? attribute.getCode() : null;
        }

        @Override
        public OrderStatus convertToEntityAttribute(String dbData) {
            return dbData != null ? OrderStatus.from(dbData) : null;
        }
    }

}