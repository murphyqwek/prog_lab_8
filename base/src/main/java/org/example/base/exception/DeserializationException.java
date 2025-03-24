package org.example.base.exception;

/**
 * DeserializationException - исключение, когда не удалось десериализовать объект
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class DeserializationException extends RuntimeException {
    public DeserializationException(String message) {
        super(message);
    }
}
