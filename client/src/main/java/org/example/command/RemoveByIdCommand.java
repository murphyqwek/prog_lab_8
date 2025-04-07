package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.iomanager.IOManager;
import org.example.base.response.ClientCommandRequest;
import org.example.network.NetworkClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * RemoveByIdCommand - класс команды для удаления элемента из коллекции по его id
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class RemoveByIdCommand extends NetworkUserCommand {
    private NetworkClient networkClient;

    /**
     * Конструктор класса
     * @param networkClient класс для управления коллекцией
     * @param out класс для работы с вводом-выводом
     */
    public RemoveByIdCommand(NetworkClient networkClient, IOManager out) {
        super("remove_by_id", "remove_by_id id : удалить элемент из коллекции по его id", out, networkClient);

        this.networkClient = networkClient;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public void execute(List<String> args) throws CommandArgumentExcetpion {
        var clientResponse = getClientCommandRequest(args);

        sendClientCommandResponse(clientResponse);
    }

    /**
     * Метод для формирования клиентского запроса
     *
     * @param args
     * @return
     */
    @Override
    public ClientCommandRequest getClientCommandRequest(List<String> args) throws CommandArgumentExcetpion {
        if(args.size() != 1) {
            throw new CommandArgumentExcetpion("Неверное количество аргументов");
        }

        String idString = args.get(0);

        int id;
        try {
            id = Integer.parseInt(idString);
        }
        catch (NumberFormatException e) {
            throw new CommandArgumentExcetpion("id должно быть числом");
        }

        List<Serializable> arguments = new ArrayList<>();
        arguments.add(id);

        ClientCommandRequest response = new ClientCommandRequest(this.getName(), arguments);
        return response;
    }
}
