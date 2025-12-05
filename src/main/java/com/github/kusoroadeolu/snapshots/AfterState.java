package com.github.kusoroadeolu.snapshots;

import java.util.List;

public record AfterState(
        List<FieldMetadata> metadata,
        long exitedAt
) {
}
