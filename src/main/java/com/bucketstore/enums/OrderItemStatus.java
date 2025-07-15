package com.bucketstore.enums;

import com.bucketstore.ports.DisplayableCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

public enum OrderItemStatus implements DisplayableCode {
    ORDERED("ORDERED", "주문됨"),
    CANCELED("CANCELED", "취소됨");

    private final String code;
    private final String label;

    OrderItemStatus(String code, String label) {
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

    public static OrderItemStatus from(String code) {
        return Arrays.stream(values())
                .filter(status -> status.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(ORDERED);
    }

    @Converter(autoApply = false)
    public static class ConverterImpl implements AttributeConverter<OrderItemStatus, String> {

        @Override
        public String convertToDatabaseColumn(OrderItemStatus attribute) {
            return attribute != null ? attribute.getCode() : null;
        }

        @Override
        public OrderItemStatus convertToEntityAttribute(String dbData) {
            return dbData != null ? OrderItemStatus.from(dbData) : null;
        }
    }
}

