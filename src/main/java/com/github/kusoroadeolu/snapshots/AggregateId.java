package com.github.kusoroadeolu.snapshots;

public record AggregateId(
        Class<?> clazz,
        Object id
) {
}
