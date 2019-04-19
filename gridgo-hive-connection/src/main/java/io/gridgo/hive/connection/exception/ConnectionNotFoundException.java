package io.gridgo.hive.connection.exception;

public class ConnectionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4896529880865658743L;

    public ConnectionNotFoundException() {
        super();
    }

    public ConnectionNotFoundException(String message) {
        super(message);
    }

    public ConnectionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionNotFoundException(Throwable cause) {
        super(cause);
    }
}
