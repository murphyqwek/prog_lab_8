package org.example.command;

import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseWithMusicBandList;
import org.example.exception.ServerErrorResponseExcpetion;
import org.example.network.NetworkClient;

import java.util.Collections;

/**
 * InfoUserCommand - класс команды для вывода информации о коллекции (тип, дата инициализации, количество элементов и т.д.)
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class InfoAppCommand {
    private String name;
    private NetworkClient networkClient;

    /**
     * Конструктор класса
     */
    public InfoAppCommand(NetworkClient networkClient) {
        this.name = "info";
        this.networkClient = networkClient;
    }

    public ServerResponse appExecute() {
        ClientCommandRequest clientCommandRequest = new ClientCommandRequest(name, Collections.emptyList());
        networkClient.sendUserCommand(clientCommandRequest);

        var response = networkClient.getServerResponse();

        return response;
    }

}
