package com.github.kusoroadeolu.events;

import java.time.LocalDateTime;


public record Event(
        EventType eventType,
        long timeToExecute, //Time taken in millis for the whole method to exec
        Object[] methodArgs,
        BeforeState beforeState,
        AfterState afterState,
        LocalDateTime capturedAt
) {
}
