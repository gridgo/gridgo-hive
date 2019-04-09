package io.gridgo.hive.client.connection.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cliffc.high_scale_lib.NonBlockingHashMap;

import io.gridgo.connector.ConnectorResolver;
import io.gridgo.hive.client.connection.ConnectorResolverAware;
import io.gridgo.hive.client.connection.HiveConnection;
import io.gridgo.hive.client.connection.HiveConnectionFactory;
import io.gridgo.hive.client.connection.RegisterHiveConnection;
import io.gridgo.hive.client.exception.CreateHiveConnectionException;
import io.gridgo.hive.client.exception.SchemeHandlerExistException;
import io.gridgo.hive.client.exception.SchemeNotRegisterException;
import io.gridgo.utils.helper.Loggable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public abstract class RegistryHiveConnectionFactory implements HiveConnectionFactory, ConnectorResolverAware, Loggable {

    private static final Pattern ENDPOINT_SCHEME_PATTERN = Pattern.compile("^([a-zA-Z0-9]+)");

    @Setter
    @Getter(AccessLevel.PROTECTED)
    private ConnectorResolver connectorResolver;

    private final Map<String, Class<? extends HiveConnection>> registry = new NonBlockingHashMap<>();

    /**
     * Auto register for class which annotated by RegisterHiveConnection
     * 
     * @param clazz
     */
    @SuppressWarnings("unchecked")
    public void register(@NonNull Class<?> clazz) {
        if (!HiveConnection.class.isAssignableFrom(clazz)) {
            if (getLogger().isWarnEnabled()) {
                getLogger().warn("Class {} annotated by {}, but doesn't implement {}", clazz, RegisterHiveConnection.class, HiveConnection.class);
            }
            return;
        }
        String[] schemes = clazz.getAnnotation(RegisterHiveConnection.class).value();
        if (schemes != null && schemes.length > 0) {
            for (String scheme : schemes) {
                this.register(scheme, (Class<? extends HiveConnection>) clazz);
            }
        }
    }

    /**
     * Register a handler class for specified scheme
     * 
     * @param scheme
     * @param clazz
     */
    public void register(@NonNull String scheme, @NonNull Class<? extends HiveConnection> clazz) {
        var old = this.registry.putIfAbsent(scheme, clazz);
        if (old != null) {
            throw new SchemeHandlerExistException("Scheme " + scheme + " already registered with handler class: " + old);
        }
    }

    public Class<? extends HiveConnection> lookup(@NonNull String scheme) {
        return this.registry.get(scheme);
    }

    public Class<? extends HiveConnection> lookupMandatory(@NonNull String scheme) {
        var result = lookup(scheme);
        if (result == null) {
            throw new SchemeNotRegisterException("Scheme " + scheme + " weren't registered");
        }
        return result;
    }

    protected String extractScheme(String endpoint) {
        Matcher matcher = ENDPOINT_SCHEME_PATTERN.matcher(endpoint);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @Override
    public HiveConnection newConnection(@NonNull String endpoint) {
        var scheme = extractScheme(endpoint);
        if (scheme == null) {
            throw new RuntimeException("Endpoint doesn't contain valid scheme");
        }
        var clazz = this.lookupMandatory(scheme);
        try {
            var connection = clazz.getConstructor().newInstance();
            if (connection instanceof ConnectorResolverAware) {
                ((ConnectorResolverAware) connection).setConnectorResolver(getConnectorResolver());
            }
            connection.connect(endpoint);
            return connection;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new CreateHiveConnectionException("Cannot create HiveConnection instance from class " + clazz);
        }
    }
}
