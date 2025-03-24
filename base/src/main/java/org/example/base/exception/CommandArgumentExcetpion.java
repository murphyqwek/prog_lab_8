package org.example.base.exception;

/**
 * CommandArgumentExcetpion - исключение, когда команде были переданы неправильные аргументы
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class CommandArgumentExcetpion extends RuntimeException {
    public CommandArgumentExcetpion(String message) {
        super(message);
    }
}
