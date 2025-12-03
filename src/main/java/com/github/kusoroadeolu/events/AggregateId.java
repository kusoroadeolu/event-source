package com.github.kusoroadeolu.events;

public record AggregateId(
        Class<?> clazz,
        Object id
) {
}
