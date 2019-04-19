package io.gridgo.hive.connection;

import org.joo.promise4j.Promise;

import io.gridgo.bean.BElement;
import io.gridgo.hive.connection.observable.EventObservable;

public interface HiveConnection extends EventObservable<BElement> {

    Promise<Boolean, Exception> start(String endpoint);

    void close() throws Exception;

    Promise<Boolean, Exception> send(BElement data);
}
