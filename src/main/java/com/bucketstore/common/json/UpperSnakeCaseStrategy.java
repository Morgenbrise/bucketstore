package com.bucketstore.common.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class UpperSnakeCaseStrategy extends PropertyNamingStrategies.NamingBase {

    @Override
    public String translate(String input) {
        if (input == null) return input;

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c) && i != 0) {
                result.append('_');
            }
            result.append(Character.toUpperCase(c));
        }

        return result.toString();
    }

    // Optional: make sure @JsonProperty is prioritized
    @Override
    public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
        JsonProperty ann = field.getAnnotation(JsonProperty.class);
        return ann != null ? ann.value() : translate(defaultName);
    }

    @Override
    public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        JsonProperty ann = method.getAnnotation(JsonProperty.class);
        return ann != null ? ann.value() : translate(defaultName);
    }

    @Override
    public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        JsonProperty ann = method.getAnnotation(JsonProperty.class);
        return ann != null ? ann.value() : translate(defaultName);
    }
}