package com.github.kusoroadeolu;

import com.github.kusoroadeolu.snapshots.AggregateId;
import com.github.kusoroadeolu.snapshots.Snapshot;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SnapshotStoreHolder {
    private static final PreConfiguredSnapshotStore STORE = Holder.INSTANCE;

    private static PreConfiguredSnapshotStore store(){
        return STORE;
    }

    private SnapshotStoreHolder(){}

    public static Map<AggregateId, List<Snapshot>> getEvents(){
        return store().getEvents();
    }

    public static AggregateId getId(Class<?> clazz, Object id){
        return store().getAggregateId(clazz, id);
    }

    public static void addEvent(AggregateId aggregateId, Snapshot snapshot){
        Objects.requireNonNull(aggregateId, "Aggregate ID cannot be null");
        Objects.requireNonNull(snapshot, "Snapshot cannot be null");
        store().addEvent(aggregateId, snapshot);
    }

     private static final class Holder {
        private final static PreConfiguredSnapshotStore INSTANCE = new PreConfiguredSnapshotStore();
     }
}
