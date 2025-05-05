package org.example.command;

import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponse;
import org.example.network.NetworkClient;

import java.util.Collections;

/**
 * SumOfAlbumsAppCommand - описание класса.
 *
 * @version 1.0
 */

public class SumOfAlbumsAppCommand {
    private String name;
    private NetworkClient networkClient;

    /**
     * Конструктор класса
     */
    public SumOfAlbumsAppCommand(NetworkClient networkClient) {
        this.name = "sum_of_albums_count";
        this.networkClient = networkClient;
    }

    public ServerResponse appExecute() {
        ClientCommandRequest clientCommandRequest = new ClientCommandRequest(name, Collections.emptyList());
        networkClient.sendUserCommand(clientCommandRequest);

        var response = networkClient.getServerResponse();

        return response;
    }
}
