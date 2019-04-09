package io.gridgo.hive.client.exception;

public class SchemeHandlerExistException extends RuntimeException {

    private static final long serialVersionUID = 3263953748573727957L;

    public SchemeHandlerExistException() {
        super();
    }

    public SchemeHandlerExistException(String message) {
        super(message);
    }

    public SchemeHandlerExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchemeHandlerExistException(Throwable cause) {
        super(cause);
    }
}
