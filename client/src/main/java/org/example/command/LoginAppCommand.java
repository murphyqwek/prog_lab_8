package org.example.command;

import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponse;
import org.example.network.NetworkClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * LoginAppCommand - описание класса.
 *
 * @version 1.0
 */

public class LoginAppCommand {
    private NetworkClient networkClient;
    private final String name = "login";

    public LoginAppCommand(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    public ServerResponse app_execute(String login, String password) {
        List<Serializable> arguments = new ArrayList<>();
        arguments.add(login);
        arguments.add(password);

        ClientCommandRequest request = new ClientCommandRequest(name, arguments);
        networkClient.sendUserCommand(request);

        return networkClient.getServerResponse();
    }
}
