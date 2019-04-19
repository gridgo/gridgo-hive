package io.gridgo.hive.connection.observable;

public interface EventDispatcher<EventType> {

    void dispatchEvent(EventType event);
}
