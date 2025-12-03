package com.github.kusoroadeolu;

import com.github.kusoroadeolu.annotations.EventSource;
import com.github.kusoroadeolu.annotations.Mutates;
import com.github.kusoroadeolu.events.AfterState;
import com.github.kusoroadeolu.events.BeforeState;
import com.github.kusoroadeolu.events.Event;
import com.github.kusoroadeolu.events.FieldMetadata;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.kusoroadeolu.EventStoreHolder.addEvent;
import static java.lang.System.currentTimeMillis;
import static java.time.LocalDateTime.now;

public class EventAdvisor {


    @Advice.OnMethodEnter
    public static BeforeState onEnter(@Advice.This Object self) throws IllegalAccessException {

        final Field[] fields = self.getClass().getDeclaredFields();
        final List<FieldMetadata> md = new ArrayList<>();
        for (Field f : fields){
            f.setAccessible(true);
            md.add(new FieldMetadata(f.getType(), f.get(self), f.getName()));
        }

       return new BeforeState(md, currentTimeMillis());
    }


    @Advice.OnMethodExit
    public static void onExit(@Advice.This Object self, @Advice.AllArguments Object[] args, @Advice.Origin String origin ,@Advice.Enter BeforeState bs) throws IllegalAccessException {
        final Field[] fields = self.getClass().getDeclaredFields();
        final List<FieldMetadata> md = new ArrayList<>();
        for (Field f : fields){
            f.setAccessible(true);
            md.add(new FieldMetadata(f.getType(), f.get(self), f.getName()));
        }

        final AfterState as = new AfterState(md, currentTimeMillis());
        final long tte = as.exitedAt() - bs.beganAt();
        final Event event = new Event(null, tte, args, bs, as, now()); //TODO get method reference and begin working on event sourcing
        addEvent("", event);
    }


}
