package io.gridgo.hive.connection.factory;

import io.gridgo.hive.connection.HiveConnection;

public interface HiveConnectionFactory {

    HiveConnection newConnection(String endpoint);
}
