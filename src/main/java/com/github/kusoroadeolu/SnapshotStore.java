package com.github.kusoroadeolu;

import com.github.kusoroadeolu.snapshots.AggregateId;
import com.github.kusoroadeolu.snapshots.Snapshot;

import java.util.List;
import java.util.Map;

public interface SnapshotStore {
    AggregateId getAggregateId(Class<?> clazz, Object id);

    void addEvent(AggregateId aggregateId, Snapshot e);

    Map<AggregateId, List<Snapshot>> getEvents();
}
