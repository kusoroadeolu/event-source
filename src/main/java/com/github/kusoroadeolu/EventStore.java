package com.github.kusoroadeolu;

import com.github.kusoroadeolu.events.AggregateId;
import com.github.kusoroadeolu.events.Event;

import java.util.List;
import java.util.Map;

public interface EventStore {
    AggregateId getAggregateId(Class<?> clazz, Object id);

    void addEvent(AggregateId aggregateId, Event e);

    Map<AggregateId, List<Event>> getEvents();
}
