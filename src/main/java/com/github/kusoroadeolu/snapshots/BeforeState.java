package com.github.kusoroadeolu.snapshots;

import java.util.List;

public record BeforeState(
        List<FieldMetadata> metadata,
        long beganAt
) {
}
