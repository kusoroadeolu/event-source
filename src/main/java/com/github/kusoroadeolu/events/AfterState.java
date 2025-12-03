package com.github.kusoroadeolu.events;

import java.util.List;

public record AfterState(
        List<FieldMetadata> metadata,
        long exitedAt
) {
}
