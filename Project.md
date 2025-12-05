Core Engine:
Proper aggregate identity system - every @EventSource class needs an ID field (maybe @AggregateId annotation)
Event stream storage: Map<AggregateId, List<Event>> with proper ordering/sequence numbers
Replay mechanism that reconstructs aggregates from their snapshot stream
Optimistic concurrency - version numbers on aggregates, throw exception if you try to mutate stale state

Event Store Features:
Append-only semantics with position/sequence tracking
Query by aggregate ID, by snapshot type, by time range
Event serialization (probably just Java serialization for v1, but design for pluggability)
Basic snapshot system - every N events, serialize full aggregate state

Projections:
Simple projection API - "subscribe to these snapshot types and build a read model"
At least one built-in projection type (maybe in-memory view models)
Projection rebuilding from snapshot history

Developer Experience:
Command/Event separation - commands trigger methods, events are what get stored
Maybe add @CommandHandler alongside @Mutates to be explicit about intent
Event metadata - causation ID, correlation ID, user/actor info
Better snapshot naming - derive from method + maybe a counter for the same method