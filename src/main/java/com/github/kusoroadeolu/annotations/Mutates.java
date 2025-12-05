package com.github.kusoroadeolu.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotates a method as a state changing method
 * </br> Any class which was annotated as {@link SnapSource}, its methods which are annotated with this will be picked up by the agent for inspection
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Mutates {
    String value();
}
