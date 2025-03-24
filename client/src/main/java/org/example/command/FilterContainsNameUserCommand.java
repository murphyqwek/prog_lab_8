package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.iomanager.IOManager;
import org.example.base.response.ClientCommandRequest;
import org.example.network.NetworkClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * FilterContainsNameUserCommand - класс команды для нахождения элементов, у которых поле name содержит заданную подстроку
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class FilterContainsNameUserCommand extends UserCommand {
    private NetworkClient networkClient;
    /**
     * Конструктор класса
     * @param networkClient класс для управления коллекцией
     * @param out класс для работы с вводом-выводом
     */
    public FilterContainsNameUserCommand(NetworkClient networkClient, IOManager out) {
        super("filter_contains_name", "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку", out);
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
        String argument = String.join(" ", args);
        if(argument.isEmpty()) {
            throw new CommandArgumentExcetpion("Неверный аргумент");
        }

        List<Serializable> arguments = new ArrayList<>();
        arguments.add(argument);

        ClientCommandRequest response = new ClientCommandRequest(this.getName(), arguments);

        networkClient.sendUserCommand(response);
        ioManager.writeLine("Отправка команды...");

        printResponse(networkClient);
    }
}
