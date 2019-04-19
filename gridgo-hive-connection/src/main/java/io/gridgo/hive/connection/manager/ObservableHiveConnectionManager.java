package io.gridgo.hive.connection.manager;

import io.gridgo.connector.ConnectorResolver;
import io.gridgo.hive.connection.ConnectorResolverAware;
import io.gridgo.hive.connection.factory.HiveConnectionFactory;
import io.gridgo.hive.connection.observable.EventObservableDispatcher;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public abstract class ObservableHiveConnectionManager extends EventObservableDispatcher<HiveTransportMessage> implements HiveConnectionManager, ConnectorResolverAware {

    @Getter(AccessLevel.PROTECTED)
    private ConnectorResolver connectorResolver;

    @Getter
    private final HiveConnectionFactory connectionFactory;

    protected ObservableHiveConnectionManager(HiveConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void setConnectorResolver(@NonNull ConnectorResolver connectorResolver) {
        this.connectorResolver = connectorResolver;
        if (this.connectionFactory instanceof ConnectorResolverAware) {
            ((ConnectorResolverAware) this.connectionFactory).setConnectorResolver(connectorResolver);
        }
    }
}
