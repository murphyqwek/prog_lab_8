package org.example.command;

import org.example.exception.CouldnotSendExcpetion;
import org.example.base.response.ClientCommandRequest;
import org.example.network.NetworkClient;
import org.example.base.exception.CommandArgumentExcetpion;

import org.example.base.iomanager.IOManager;

import java.util.Collections;
import java.util.List;

/**
 * UserCommandWithoutArguments - класс команд пользователя, которые не принимают аргументы, только отправляют название команды на сервер.
 *
 * @version 1.0
 */

public class UserCommandWithoutArguments extends UserCommand {
    private final NetworkClient networkClient;

    /**
     * Конструктор класса
     *
     * @param name        имя команды
     * @param description описание команды
     * @param ioManager   класс для работы с вводом-выводом
     */
    public UserCommandWithoutArguments(String name, String description, IOManager ioManager, NetworkClient networkClient) {
        super(name, description, ioManager);
        this.networkClient = networkClient;
    }

    @Override
    public void execute(List<String> args) throws CommandArgumentExcetpion, CouldnotSendExcpetion {
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion("Команда не принимает никаких аргументов");
        }

        ClientCommandRequest clientCommandRequest = new ClientCommandRequest(this.getName(), Collections.emptyList());

        networkClient.sendUserCommand(clientCommandRequest);
        ioManager.writeLine("Отправка команды...");

        printResponse(networkClient);
    }
}
