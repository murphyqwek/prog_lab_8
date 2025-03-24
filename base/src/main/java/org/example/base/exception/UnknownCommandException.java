package org.example.base.exception;

/**
 * UnknownCommandException - исключение, когда не удается распознать команду
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class UnknownCommandException extends RuntimeException {
    public UnknownCommandException(String commandName) {
        super(String.format("Неизвестная команда '%s'", commandName));
    }
}
