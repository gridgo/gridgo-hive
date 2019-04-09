package io.gridgo.hive.client.connection;

import java.util.function.BiConsumer;

import io.gridgo.bean.BElement;
import io.gridgo.hive.client.connection.impl.SimpleHiveConnectionManager;

public interface HiveConnectionManager {

    static HiveConnectionManager newDefault() {
        return new SimpleHiveConnectionManager();
    }

    HiveConnectionFactory getConnectionFactory();

    int connect(String endpoint);

    void sendTo(int connectionId, BElement data);

    /**
     * add listener for all event
     * 
     * @param consumer
     * @return listenerId, will be used in removeListener method
     */
    int addListener(BiConsumer<Integer, BElement> consumer);

    void removeListener(int listenerId);
}
