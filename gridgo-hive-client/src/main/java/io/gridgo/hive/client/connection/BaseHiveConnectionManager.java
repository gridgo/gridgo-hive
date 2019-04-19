package io.gridgo.hive.client.connection;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.cliffc.high_scale_lib.NonBlockingHashMap;
import org.joo.promise4j.Promise;

import io.gridgo.hive.connection.HiveConnection;
import io.gridgo.hive.connection.exception.ConnectionNotFoundException;
import io.gridgo.hive.connection.factory.HiveConnectionFactory;
import io.gridgo.hive.connection.manager.HiveTransportMessage;
import io.gridgo.hive.connection.manager.ObservableHiveConnectionManager;
import lombok.NonNull;

public class BaseHiveConnectionManager extends ObservableHiveConnectionManager implements HiveClientConnectionManager {

    private final AtomicInteger _connectionIdSeed = new AtomicInteger(0);

    private final Map<Integer, HiveConnection> connections = new NonBlockingHashMap<>();

    public BaseHiveConnectionManager(HiveConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    private int registerConnection(@NonNull HiveConnection connection) {
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

    @Override
    public int connect(String endpoint) {
        var connection = getConnectionFactory().newConnection(endpoint);
        this.prepareConnection(connection);
        final int connectionId = this.registerConnection(connection);
        connection.addEventListener((data) -> {
            dispatchEvent(HiveTransportMessage.newDefault(connectionId, data));
        });
        return connectionId;
    }

    @Override
    public Promise<Boolean, Exception> send(@NonNull HiveTransportMessage message) {
        return this.lookupConnectionMandatory(message.getConnectionId()).send(message.getPayload());
    }
}
