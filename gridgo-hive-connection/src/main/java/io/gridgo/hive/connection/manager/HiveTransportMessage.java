package io.gridgo.hive.connection.manager;

import io.gridgo.bean.BElement;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface HiveTransportMessage {

    static HiveTransportMessage newDefault(int connectionId, BElement payload) {
        return new DefaultHiveTransportMessage(connectionId, payload);
    }

    int getConnectionId();

    BElement getPayload();
}

@Getter
@AllArgsConstructor
class DefaultHiveTransportMessage implements HiveTransportMessage {
    private final int connectionId;
    private final BElement payload;
}
