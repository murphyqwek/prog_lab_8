package org.example.command;

import org.example.base.iomanager.IOManager;
import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponseType;
import org.example.exception.CouldnotSendExcpetion;
import org.example.network.NetworkClient;

/**
 * NetworkUserCommand - описание класса.
 *
 * @version 1.0
 */

public abstract class NetworkUserCommand extends UserCommand implements RequestableCommand {
    protected final NetworkClient networkClient;
    /**
     * Конструктор класса
     *
     * @param name        имя команды
     * @param description описание команды
     * @param ioManager   класс для работы с вводом-выводом
     */
    public NetworkUserCommand(String name, String description, IOManager ioManager, NetworkClient networkClient) {
        super(name, description, ioManager);
        this.networkClient = networkClient;
    }

    public void sendClientCommandResponse(ClientCommandRequest clientCommandRequest) throws CouldnotSendExcpetion {
        networkClient.sendUserCommand(clientCommandRequest);
        ioManager.writeLine("Отправка команды...");

        printResponse(networkClient);
    }

    public void printResponse(NetworkClient networkClient) {
        var response = networkClient.getServerResponse();

        if(response.getType() == ServerResponseType.CORRUPTED) {
            ioManager.writeError(response.getMessage());
            return;
        }

        ioManager.writeLine(response.getMessage());
    }
}
