package io.gridgo.hive.server.connection;

import io.gridgo.hive.connection.manager.HiveConnectionManager;

public interface HiveServerConnectionManager extends HiveConnectionManager {

    void bind(String endpoint);
}
