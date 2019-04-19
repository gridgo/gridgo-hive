package io.gridgo.hive.client.connection.impl;

import io.gridgo.bean.BElement;
import io.gridgo.connector.Connector;
import io.gridgo.connector.Producer;
import io.gridgo.framework.support.Message;
import io.gridgo.hive.client.connection.RegisterHiveClientConnection;

@RegisterHiveClientConnection({ "ws", "wss" })
public class Netty4WebsocketHiveConnection extends AbstractHiveClientConnection {

    private static final String NETTY4_SCHEME = "netty4:";
    private Connector connector;
    private Producer producer;

    @Override
    public void connect(String endpoint) {
        this.connector = this.getConnectorResolver().resolve(NETTY4_SCHEME + endpoint);
        this.producer = this.connector.getProducer().get();
    }

    @Override
    public void close() {
        this.connector.stop();
    }

    @Override
    public void send(BElement data) {
        this.producer.send(Message.ofAny(data));
    }
}
