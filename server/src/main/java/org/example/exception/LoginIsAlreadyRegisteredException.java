package org.example.exception;

/**
 * LoginIsAlreadyRegisteredException - исключение, когда при попытке зарегистрироваться используется логин, который уже есть в базе данных
 */
public class LoginIsAlreadyRegisteredException extends RuntimeException {
    public LoginIsAlreadyRegisteredException() {
        super();
    }

    public LoginIsAlreadyRegisteredException(String message) {
        super(message);
    }
}
