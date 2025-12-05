package com.github.kusoroadeolu;

import com.github.kusoroadeolu.snapshots.AggregateId;
import com.github.kusoroadeolu.snapshots.Snapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Stores events nothing else
public class PreConfiguredSnapshotStore implements SnapshotStore {
    private final Map<AggregateId, List<Snapshot>> eventMap;
    private final Map<AggregateId, Lock> locks;


    public PreConfiguredSnapshotStore(Map<AggregateId, List<Snapshot>> map){
        this.eventMap = map;
        this.locks = new ConcurrentHashMap<>();

    }

    public PreConfiguredSnapshotStore(){
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

    public void addEvent(AggregateId aggregateId, Snapshot e){
       final Lock lock = this.locks.computeIfAbsent(aggregateId, (_) -> new ReentrantLock());
       lock.lock();
       try {
           this.eventMap.computeIfAbsent(aggregateId, (_) -> new ArrayList<>()).add(e);
       }finally {
           lock.unlock();
       }
    }

    public Map<AggregateId, List<Snapshot>> getEvents(){
        return Map.copyOf(this.eventMap);
    }

}
