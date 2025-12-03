package com.github.kusoroadeolu;

import com.github.kusoroadeolu.annotations.AggregateIdentity;
import com.github.kusoroadeolu.annotations.EventSource;
import com.github.kusoroadeolu.annotations.Mutates;

import java.time.LocalDateTime;

@EventSource(Integer.class)
public class Foo {
    @AggregateIdentity
    private final int fooId = 1;

    private String name;
    private LocalDateTime time;

    @Mutates("default")
    public String test(String name, int id, LocalDateTime now){
        this.name = name;
        this.time = now;
        return name;
    }

    @Mutates("default")
    public String test(String name, int id){
        return test(name, id, LocalDateTime.now());
    }
}
