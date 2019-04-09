package io.gridgo.hive.client.connection.impl;

import java.util.Set;

import org.reflections.Reflections;

import io.gridgo.hive.client.connection.HiveConnection;
import io.gridgo.hive.client.connection.RegisterHiveConnection;
import lombok.NonNull;

public class AutoScanHiveConnectionFactory extends RegistryHiveConnectionFactory {

    public static final String DEFAULT_PACKAGE = HiveConnection.class.getPackageName();

    public AutoScanHiveConnectionFactory() {
        this.scan(DEFAULT_PACKAGE);
    }

    public void scan(@NonNull String packageName) {
        this.scan(packageName, null);
    }

    public void scan(@NonNull String packageName, ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }

        Reflections reflections = new Reflections(packageName, classLoader);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(RegisterHiveConnection.class);
        for (Class<?> clazz : classes) {
            this.register(clazz);
        }
    }
}
