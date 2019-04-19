package io.gridgo.hive.connection;

import io.gridgo.connector.ConnectorResolver;

public interface ConnectorResolverAware {

    void setConnectorResolver(ConnectorResolver resolver);
}
