package org.example.command;

import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.network.NetworkClient;

import java.util.Collections;

/**
 * ShowOwnersAppCommand - описание класса.
 *
 * @version 1.0
 */

public class UnsubscribeAppCommand {
    private NetworkClient networkClient;
    private final String name = "unsubscribe";

    public UnsubscribeAppCommand(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    public ServerResponse appExecute() {
        ClientCommandRequest clientCommandRequest = new ClientCommandRequest(name, Collections.emptyList());
        networkClient.sendUserCommand(clientCommandRequest);

        var response = new ServerResponse(ServerResponseType.SUCCESS, "");

        return response;
    }
}
