package io.gridgo.hive.client.connection;

import io.gridgo.bean.BElement;

public interface HiveConnection {

    void connect(String endpoint);

    void send(BElement data);
}
