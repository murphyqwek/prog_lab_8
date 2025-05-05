package org.example.command;

import org.example.base.response.*;
import org.example.exception.ServerErrorResponseExcpetion;
import org.example.network.NetworkClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ShowOwnersAppCommand - описание класса.
 *
 * @version 1.0
 */

public class ShowOwnersAppCommand {
    private NetworkClient networkClient;
    private final String name = "show_with_owners";

    public ShowOwnersAppCommand(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    public ServerResponse appExecute() {
        ClientCommandRequest clientCommandRequest = new ClientCommandRequest(name, Collections.emptyList());
        networkClient.sendUserCommand(clientCommandRequest);

        var response = networkClient.getServerResponse();

        return response;
    }
}
