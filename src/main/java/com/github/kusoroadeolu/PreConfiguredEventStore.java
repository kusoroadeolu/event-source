package com.github.kusoroadeolu;

import com.github.kusoroadeolu.events.AggregateId;
import com.github.kusoroadeolu.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Stores events nothing else
public class PreConfiguredEventStore implements EventStore{
    private final Map<AggregateId, List<Event>> eventMap;
    private final Map<AggregateId, Integer> versions;
    private final Map<AggregateId, Lock> locks;


    public PreConfiguredEventStore(Map<AggregateId, List<Event>> map){
        this.eventMap = map;
        this.locks = new ConcurrentHashMap<>();
        this.versions = new ConcurrentHashMap<>();
    }

    public PreConfiguredEventStore(){
        this(new ConcurrentHashMap<>());
    }

    public AggregateId getAggregateId(Class<?> clazz, Object id){
        Optional<AggregateId> op = this.eventMap
                .keySet()
                .stream()
                .filter(a -> id.equals(a.id()) && clazz.equals(a.clazz()))
                .findFirst();

        return op.orElse(new AggregateId(clazz, id));
    }

    public void addEvent(AggregateId aggregateId, Event e){
       final Lock lock = this.locks.computeIfAbsent(aggregateId, (_) -> new ReentrantLock());
       lock.lock();
       try {
           this.eventMap.computeIfAbsent(aggregateId, (_) -> new ArrayList<>()).add(e);
       }finally {
           lock.unlock();
       }
    }

    public Map<AggregateId, List<Event>> getEvents(){
        return Map.copyOf(this.eventMap);
    }

}
