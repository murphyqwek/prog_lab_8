package org.example;

/**
 * UserLoginPasswordContainer - класс-контейнер для хранения данных для авторизации.
 *
 * @version 1.0
 */

public class UserLoginPasswordContainer {
    private String login;
    private String password;

    public UserLoginPasswordContainer(String login, String password) {
        if(login == null || password == null) {
            throw new IllegalArgumentException("login and password cannot be null");
        }
        this.login = login;
        this.password = password;
    }

    public UserLoginPasswordContainer() {
        login = "";
        password = "";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if(login == null) {
            throw new NullPointerException("login is null");
        }
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password == null) {
            throw new NullPointerException("password is null");
        }
        this.password = password;
    }
}
