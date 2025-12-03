package com.github.kusoroadeolu.events;

import java.util.List;

public record BeforeState(
        List<FieldMetadata> metadata,
        long beganAt
) {
}
