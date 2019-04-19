package io.gridgo.hive.client.connection;

import io.gridgo.hive.connection.manager.HiveConnectionManager;

public interface HiveClientConnectionManager extends HiveConnectionManager {

    int connect(String endpoint);
}
