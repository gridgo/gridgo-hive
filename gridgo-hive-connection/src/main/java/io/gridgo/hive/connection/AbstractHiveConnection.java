package io.gridgo.hive.connection;

import io.gridgo.bean.BElement;
import io.gridgo.connector.ConnectorResolver;
import io.gridgo.hive.connection.observable.EventObservableDispatcher;
import io.gridgo.utils.helper.Loggable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractHiveConnection extends EventObservableDispatcher<BElement> implements HiveConnection, ConnectorResolverAware, Loggable {

    @Setter
    @Getter(AccessLevel.PROTECTED)
    private ConnectorResolver connectorResolver;
}
