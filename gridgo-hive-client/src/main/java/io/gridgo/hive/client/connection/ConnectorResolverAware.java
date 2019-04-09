package io.gridgo.hive.client.connection;

import io.gridgo.connector.ConnectorResolver;

public interface ConnectorResolverAware {

    void setConnectorResolver(ConnectorResolver resolver);
}
