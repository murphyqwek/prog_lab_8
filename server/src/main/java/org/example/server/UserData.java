package org.example.server;

import java.net.InetAddress;
import java.time.Instant;
import java.util.HashMap;

/**
 * UserData - класс хранилища данных пользователя.
 *
 * @version 1.0
 */

public class UserData {
    private String login;
    private String password;
    private InetAddress inetAddress;
    private volatile Instant lastActive;

    public UserData(String login, String password, InetAddress inetAddress) {
        this.login = login;
        this.password = password;
        this.inetAddress = inetAddress;
        this.lastActive = Instant.now();
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void updateActivity() {
        this.lastActive = Instant.now();
    }

    public Instant getLastActive() {
        return lastActive;
    }

    @Override
    public String toString() {
        return String.format("Login: %s, Password: %s, InetAddress: %s, Last Active: %s", login, password, inetAddress, lastActive.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;

        if(!(obj instanceof UserData other)) return false;

        return login.equals(other.login) && password.equals(other.password) && inetAddress.equals(other.inetAddress);
    }


    @Override
    public int hashCode() {
        return inetAddress.hashCode() | login.hashCode() | password.hashCode();
    }
}
