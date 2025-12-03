package com.github.kusoroadeolu.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Assertions {

    public static void assertFieldFinal(Field f, String message){
        if (!Modifier.isFinal(f.getModifiers())) {
            throw new IllegalStateException(message);
        }
    }

    public static void assertNotEmpty(Object[] o, String message){
        if(o.length == 0)throw new IllegalStateException(message);
    }

    public static void assertNotNull(Object o, String message){
        if(o == null) throw new IllegalStateException(message);
    }

}
