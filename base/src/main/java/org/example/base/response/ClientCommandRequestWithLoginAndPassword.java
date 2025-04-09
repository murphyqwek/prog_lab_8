package org.example.base.response;

import java.io.Serializable;
import java.util.List;

/**
 * ClientCommandRequestWithLoginAndPassword - описание класса.
 *
 * @version 1.0
 */

public class ClientCommandRequestWithLoginAndPassword extends ClientCommandRequest {
    private final ClientCommandRequest requestWrappe;
    private final String login;
    private final String password;

    public ClientCommandRequestWithLoginAndPassword(ClientCommandRequest request, String login, String password) {
        super(request.getCommandName(), request.getArguments());
        requestWrappe = request;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public List<Serializable> getArguments() {
        return requestWrappe.getArguments();
    }

    @Override
    public String getCommandName() {
        return requestWrappe.getCommandName();
    }

    @Override
    public String toString() {
        return super.getCommandName() + "{ login: " + login + "; password: " + password + "}";
    }
}
