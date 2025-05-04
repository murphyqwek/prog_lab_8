package org.example.exception;

public class WrongLoginPasswordException extends RuntimeException {
    public WrongLoginPasswordException() {
        super("Неверный пароль или логин.");
    }
}
