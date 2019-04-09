package io.gridgo.hive.client.connection.impl;

import io.gridgo.connector.ConnectorResolver;
import io.gridgo.hive.client.connection.ConnectorResolverAware;
import io.gridgo.hive.client.connection.HiveConnection;
import io.gridgo.utils.helper.Loggable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractHiveConnection implements HiveConnection, ConnectorResolverAware, Loggable {

    @Setter
    @Getter(AccessLevel.PROTECTED)
    private ConnectorResolver connectorResolver;
}
