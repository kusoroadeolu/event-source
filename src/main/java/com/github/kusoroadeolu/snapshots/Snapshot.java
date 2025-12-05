package com.github.kusoroadeolu.snapshots;

import java.time.LocalDateTime;


public record Snapshot(
        SnapshotType snapshotType,
        long timeToExecute, //Time taken in millis for the whole method to exec
        Object[] methodArgs,
        BeforeState beforeState,
        AfterState afterState,
        LocalDateTime capturedAt
) {
}
