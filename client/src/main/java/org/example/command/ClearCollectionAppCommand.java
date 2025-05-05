package org.example.command;

import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponse;
import org.example.network.NetworkClient;

import java.util.Collections;

/**
 * ClearCollectionAppCommand - описание класса.
 *
 * @version 1.0
 */

public class ClearCollectionAppCommand {
    private String name;
    private NetworkClient networkClient;

    /**
     * Конструктор класса
     */
    public ClearCollectionAppCommand(NetworkClient networkClient) {
        this.name = "clear";
        this.networkClient = networkClient;
    }

    public ServerResponse appExecute() {
        ClientCommandRequest clientCommandRequest = new ClientCommandRequest(name, Collections.emptyList());
        networkClient.sendUserCommand(clientCommandRequest);

        var response = networkClient.getServerResponse();

        return response;
    }

}
