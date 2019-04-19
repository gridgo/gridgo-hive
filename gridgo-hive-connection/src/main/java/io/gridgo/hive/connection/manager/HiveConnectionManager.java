package io.gridgo.hive.connection.manager;

import org.joo.promise4j.Promise;

import io.gridgo.hive.connection.observable.EventObservable;

public interface HiveConnectionManager extends EventObservable<HiveTransportMessage> {

    Promise<Boolean, Exception> send(HiveTransportMessage message);
}
