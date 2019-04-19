package io.gridgo.hive.connection.observable;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.cliffc.high_scale_lib.NonBlockingHashMap;

public class EventObservableDispatcher<EventType> implements EventObservable<EventType>, EventDispatcher<EventType> {

    private final AtomicInteger _listenerIdSeed = new AtomicInteger(0);

    private final Map<Integer, Consumer<EventType>> listeners = new NonBlockingHashMap<>();

    @Override
    public Disposable addEventListener(Consumer<EventType> listener) {
        final var id = _listenerIdSeed.getAndIncrement();
        this.listeners.put(id, listener);
        return () -> {
            this.listeners.remove(id);
        };
    }

    @Override
    public void dispatchEvent(EventType event) {
        for (Consumer<EventType> consumer : this.listeners.values()) {
            consumer.accept(event);
        }
    }
}
