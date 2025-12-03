package com.github.kusoroadeolu.events;

public record FieldMetadata(
        Class<?> clazz, Object value, String fieldName
) {

}
