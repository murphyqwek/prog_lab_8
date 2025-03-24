package org.example.exception;

/**
 * ServerErrorResponse - исключения, когда сервер вернул ошибку
 */
public class ServerErrorResponseExcpetion extends RuntimeException {
    private final boolean connectionError;

    public ServerErrorResponseExcpetion(String message, boolean connectionError) {
        super(message);
        this.connectionError = connectionError;
    }

    public boolean isConnectionError() {
        return connectionError;
    }
}
