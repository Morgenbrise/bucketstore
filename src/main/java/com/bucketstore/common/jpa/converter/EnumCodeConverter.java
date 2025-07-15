package com.bucketstore.common.jpa.converter;

import com.bucketstore.ports.DisplayableCode;
import jakarta.persistence.AttributeConverter;

public abstract class EnumCodeConverter<E extends Enum<E> & DisplayableCode> implements AttributeConverter<E, String> {

    private final Class<E> enumClass;

    protected EnumCodeConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (E e : enumClass.getEnumConstants()) {
            if (e.getCode().equalsIgnoreCase(dbData)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}
