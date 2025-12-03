package com.github.kusoroadeolu;

import com.github.kusoroadeolu.events.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//Stores events nothing else
class EventStore {
    private final Map<String, Event> eventMap;

    public EventStore(Map<String, Event> map){
        this.eventMap = map;
    }

    public EventStore(){
        this(new ConcurrentHashMap<>());
    }

    public void addEvent(String eventType, Event e){
        this.eventMap.put(eventType, e);
    }

    public Map<String, Event> getEvents(){
        return Map.copyOf(this.eventMap);
    }

}
