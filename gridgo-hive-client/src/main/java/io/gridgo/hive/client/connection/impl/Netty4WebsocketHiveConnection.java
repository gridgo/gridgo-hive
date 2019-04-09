package io.gridgo.hive.client.connection.impl;

import io.gridgo.bean.BElement;
import io.gridgo.hive.client.connection.RegisterHiveConnection;

@RegisterHiveConnection({ "ws", "wss" })
public class Netty4WebsocketHiveConnection extends AbstractHiveConnection {

    @Override
    public void connect(String endpoint) {
        
    }

    @Override
    public void send(BElement data) {

    }

}
