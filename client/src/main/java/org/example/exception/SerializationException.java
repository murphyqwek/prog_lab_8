package org.example.exception;

/**
 * SerializationException - исключение, когда не удалось сериализовать объект
 */
public class SerializationException extends RuntimeException {
    public SerializationException(String message) {
        super(message);
    }
}
