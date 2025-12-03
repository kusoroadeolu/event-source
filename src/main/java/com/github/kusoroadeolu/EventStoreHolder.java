package com.github.kusoroadeolu;

import com.github.kusoroadeolu.events.AggregateId;
import com.github.kusoroadeolu.events.Event;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EventStoreHolder {
    private static final PreConfiguredEventStore STORE = Holder.INSTANCE;

    private static PreConfiguredEventStore store(){
        return STORE;
    }

    private EventStoreHolder(){}

    public static Map<AggregateId, List<Event>> getEvents(){
        return store().getEvents();
    }

    public static AggregateId getId(Class<?> clazz, Object id){
        return store().getAggregateId(clazz, id);
    }

    public static void addEvent(AggregateId aggregateId, Event event){
        Objects.requireNonNull(aggregateId, "Aggregate ID cannot be null");
        Objects.requireNonNull(event, "Event cannot be null");
        store().addEvent(aggregateId, event);
    }

     private static final class Holder {
        private final static PreConfiguredEventStore INSTANCE = new PreConfiguredEventStore();
     }
}
