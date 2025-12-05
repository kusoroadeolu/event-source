package com.github.kusoroadeolu;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bar {

    public Foo foo = new Foo();
    public ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
}
