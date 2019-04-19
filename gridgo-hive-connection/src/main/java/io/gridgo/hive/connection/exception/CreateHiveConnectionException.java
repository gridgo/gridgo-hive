package io.gridgo.hive.connection.exception;

public class CreateHiveConnectionException extends RuntimeException {

    private static final long serialVersionUID = 5745767180157900571L;

    public CreateHiveConnectionException() {
        super();
    }

    public CreateHiveConnectionException(String message) {
        super(message);
    }

    public CreateHiveConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateHiveConnectionException(Throwable cause) {
        super(cause);
    }
}
