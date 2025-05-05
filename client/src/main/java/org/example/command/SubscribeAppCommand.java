package org.example.command;

import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponse;
import org.example.network.NetworkClient;

import java.util.Collections;

/**
 * ShowOwnersAppCommand - описание класса.
 *
 * @version 1.0
 */

public class SubscribeAppCommand {
    private NetworkClient networkClient;
    private final String name = "subscribe";

    public SubscribeAppCommand(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    public ServerResponse appExecute() {
        ClientCommandRequest clientCommandRequest = new ClientCommandRequest(name, Collections.emptyList());
        networkClient.sendUserCommand(clientCommandRequest);

        var response = networkClient.getServerResponse();

        return response;
    }
}
