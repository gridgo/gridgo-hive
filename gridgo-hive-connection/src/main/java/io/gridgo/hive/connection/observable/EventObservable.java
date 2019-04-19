package io.gridgo.hive.connection.observable;

import java.util.function.Consumer;

public interface EventObservable<EventType> {

    Disposable addEventListener(Consumer<EventType> listener);
}
