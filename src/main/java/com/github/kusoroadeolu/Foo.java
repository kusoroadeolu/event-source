package com.github.kusoroadeolu;

import com.github.kusoroadeolu.annotations.EventSource;
import com.github.kusoroadeolu.annotations.Mutates;

import java.time.LocalDateTime;

@EventSource(Integer.class)
public class Foo {

    private int fooId = 0;

    @Mutates("default")
    public String test(String name, int id, LocalDateTime now){
        name = "Hello World";
        fooId = id;
        return name;
    }

    @Mutates("default")
    public String test(String name, int id){
        return test(name, id, LocalDateTime.now());
    }
}
