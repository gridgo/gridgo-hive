package io.gridgo.hive.client.connection.impl;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import org.cliffc.high_scale_lib.NonBlockingHashMap;

import io.gridgo.bean.BElement;
import io.gridgo.connector.ConnectorResolver;
import io.gridgo.hive.client.connection.ConnectorResolverAware;
import io.gridgo.hive.client.connection.HiveConnectionFactory;
import io.gridgo.hive.client.connection.HiveConnectionManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public abstract class AbstractHiveConnectionManager implements HiveConnectionManager, ConnectorResolverAware {

    @Getter(AccessLevel.PROTECTED)
    private ConnectorResolver connectorResolver;

    private final AtomicInteger _listenerIdSeed = new AtomicInteger(0);

    private final Map<Integer, BiConsumer<Integer, BElement>> listeners = new NonBlockingHashMap<>();

    @Getter
    private final HiveConnectionFactory connectionFactory = createConnectionFactory();

    protected abstract HiveConnectionFactory createConnectionFactory();

    @Override
    public void setConnectorResolver(@NonNull ConnectorResolver connectorResolver) {
        this.connectorResolver = connectorResolver;
        if (this.connectionFactory instanceof ConnectorResolverAware) {
            ((ConnectorResolverAware) this.connectionFactory).setConnectorResolver(connectorResolver);
        }
    }

    protected void dispatch(int connectionId, BElement event) {
        for (BiConsumer<Integer, BElement> consumer : this.listeners.values()) {
            consumer.accept(connectionId, event);
        }
    }

    @Override
    public int addListener(@NonNull BiConsumer<Integer, BElement> listener) {
        var id = _listenerIdSeed.getAndIncrement();
        this.listeners.put(id, listener);
        return id;
    }

    @Override
    public void removeListener(int listenerId) {
        this.listeners.remove(listenerId);
    }
}
