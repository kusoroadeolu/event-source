package com.github.kusoroadeolu.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a type as a source of events.
 * </br> Any class annotated by this will be picked up by the agent at runtime as an event source
 * */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SnapSource {
    Class<?>[] value(); //Value to know what field types you want to keep track of mutation
}
