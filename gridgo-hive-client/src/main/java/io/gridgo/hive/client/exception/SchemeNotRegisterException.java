package io.gridgo.hive.client.exception;

public class SchemeNotRegisterException extends RuntimeException {

    private static final long serialVersionUID = -6920202590245154486L;

    public SchemeNotRegisterException() {
        super();
    }

    public SchemeNotRegisterException(String message) {
        super(message);
    }

    public SchemeNotRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchemeNotRegisterException(Throwable cause) {
        super(cause);
    }
}
