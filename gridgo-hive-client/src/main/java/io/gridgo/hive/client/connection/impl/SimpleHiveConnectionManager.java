package io.gridgo.hive.client.connection.impl;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.cliffc.high_scale_lib.NonBlockingHashMap;

import io.gridgo.bean.BElement;
import io.gridgo.hive.client.connection.HiveConnection;
import io.gridgo.hive.client.connection.HiveConnectionFactory;
import io.gridgo.hive.client.exception.ConnectionNotFoundException;
import lombok.NonNull;

public class SimpleHiveConnectionManager extends AbstractHiveConnectionManager {

    private final AtomicInteger _connectionIdSeed = new AtomicInteger(0);

    private final Map<Integer, HiveConnection> connections = new NonBlockingHashMap<>();

    protected HiveConnectionFactory createConnectionFactory() {
        return new AutoScanHiveConnectionFactory();
    }

    protected int registerConnection(@NonNull HiveConnection connection) {
        int id = _connectionIdSeed.getAndIncrement();
        this.connections.put(id, connection);
        return id;
    }

    protected void deregisterConnection(int id) {
        this.connections.remove(id);
    }

    protected HiveConnection lookupConnection(int id) {
        return this.connections.get(id);
    }

    protected HiveConnection lookupConnectionMandatory(int id) {
        var result = this.lookupConnection(id);
        if (result == null) {
            throw new ConnectionNotFoundException("Connection cannot be found for id " + id);
        }
        return result;
    }

    protected void prepareConnection(@NonNull HiveConnection connection) {
        // do nothing
    }

    public int connect(String endpoint) {
        var connection = getConnectionFactory().newConnection(endpoint);
        this.prepareConnection(connection);
        return this.registerConnection(connection);
    }

    @Override
    public void sendTo(int connectionId, BElement data) {
        this.lookupConnectionMandatory(connectionId).send(data);
    }
}
