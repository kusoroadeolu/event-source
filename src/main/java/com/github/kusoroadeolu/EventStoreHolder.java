package com.github.kusoroadeolu;

import com.github.kusoroadeolu.events.Event;

import java.util.Map;
import java.util.Objects;

public class EventStoreHolder {
    private static final EventStore STORE = Holder.INSTANCE;

    private static EventStore store(){
        return STORE;
    }

    private EventStoreHolder(){}

    public static Map<String, Event> getEvents(){
        return store().getEvents();
    }

    public static void addEvent(String eventType, Event event){
        Objects.requireNonNull(eventType, "Event type cannot be null");
        Objects.requireNonNull(event, "Event cannot be null");
        store().addEvent(eventType, event);
    }

     private static final class Holder {
        private final static EventStore INSTANCE = new EventStore();
     }
}
