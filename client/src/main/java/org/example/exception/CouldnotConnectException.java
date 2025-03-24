package org.example.exception;

public class CouldnotConnectException extends RuntimeException {
    public CouldnotConnectException(String message) {
        super(message);
    }
}
