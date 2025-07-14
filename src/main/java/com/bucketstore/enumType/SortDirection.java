package com.bucketstore.enumType;

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

}
