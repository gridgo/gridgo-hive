package io.gridgo.hive.client.connection;

public interface HiveConnectionFactory {

    HiveConnection newConnection(String endpoint);
}
