package com.github.kusoroadeolu;

import com.github.kusoroadeolu.annotations.AggregateIdentity;
import com.github.kusoroadeolu.annotations.Mutates;
import com.github.kusoroadeolu.events.*;
import net.bytebuddy.asm.Advice;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.github.kusoroadeolu.EventStoreHolder.addEvent;
import static com.github.kusoroadeolu.EventStoreHolder.getId;
import static com.github.kusoroadeolu.utils.Assertions.*;
import static java.lang.System.currentTimeMillis;
import static java.time.LocalDateTime.now;

public class EventAdvisor {


    @Advice.OnMethodEnter
    public static BeforeState onEnter(@Advice.This Object self) throws IllegalAccessException {
        final Field[] fields = self.getClass().getDeclaredFields();
        assertNotEmpty(fields, "No fields found for event sourced class '%s'".formatted(self.getClass().getSimpleName()));

        final List<FieldMetadata> md = new ArrayList<>();
        for (Field f : fields){
            f.setAccessible(true);
            md.add(new FieldMetadata(f.getType(), f.get(self), f.getName()));
        }

       return new BeforeState(md, currentTimeMillis());
    }


    @Advice.OnMethodExit
    public static void onExit(@Advice.This Object self, @Advice.AllArguments Object[] args, @Advice.Origin Method origin , @Advice.Enter BeforeState bs) throws IllegalAccessException {
        final Field[] fields = self.getClass().getDeclaredFields();
        final List<FieldMetadata> md = new ArrayList<>();
        Object id = findAnnotatedAggregate(fields, self, md);
        final AggregateId aggregateId = getId(self.getClass(), id);

        final AfterState as = new AfterState(md, currentTimeMillis());
        final long tte = as.exitedAt() - bs.beganAt();
        final String eventType = origin.getAnnotation(Mutates.class).value();

        final Event event = new Event(new EventType(eventType), tte, args, bs, as, now()); //TODO Begin working on event sourcing
        addEvent(aggregateId, event);
    }


    public static Object findAnnotatedAggregate(Field[] fields, Object self, List<FieldMetadata> md) throws IllegalAccessException {
        Object id = null;
        for (Field f : fields){
            f.setAccessible(true);

            if(id == null && f.isAnnotationPresent(AggregateIdentity.class)){
                IO.println("Annotated name: " + f.getName());
                assertFieldFinal(f, "Field '%s' annotated as aggregate ID should be final".formatted(f.getName()));
                id = f.get(self);
                IO.println("Id: " + id);
            }

            md.add(new FieldMetadata(f.getType(), f.get(self), f.getName()));
        }

        assertNotNull(id, "Event sourced class must contain an aggregate ID");
        return id;
    }



}
