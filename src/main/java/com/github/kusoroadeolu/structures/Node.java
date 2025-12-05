package com.github.kusoroadeolu.structures;

import java.util.List;

public record Node(
        Object val,
        Class<?> clazz,
        String fieldName,
        List<Node> children
) {

    public boolean equals(Node other){
        return clazz.equals(other.clazz) && fieldName.equals(other.fieldName) && val.equals(other.val);
    }

}
