package com.github.kusoroadeolu.structures;

public record FieldDiff(
        String fieldName,
        Object before,
        Object diff //Basically the change

) {
}
