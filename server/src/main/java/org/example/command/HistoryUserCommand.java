package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.manager.CommandHistoryManager;

import java.io.Serializable;
import java.util.List;


/**
 * HistoryUserCommand - класс команды для вывода последних 10 команд (без их аргументов)
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class HistoryUserCommand extends UserCommand {
    private final CommandHistoryManager commandHistoryManager;

    /**
     * Конструктор класса
     * @param commandHistoryManager класс для управления историей команд
     */
    public HistoryUserCommand(CommandHistoryManager commandHistoryManager) {
        super("history", "history: вывести последние 10 команд (без их аргументов)");

        this.commandHistoryManager = commandHistoryManager;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public ServerResponse execute(List<Serializable> args) throws CommandArgumentExcetpion {
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion("Команда не принимает никаких аргументов");
        }

        StringBuilder builder = new StringBuilder();

        commandHistoryManager.getCommandHistory(10).stream().forEach(command -> builder.append(command).append("\n"));

        return new ServerResponse(ServerResponseType.SUCCESS, builder.toString());
    }
}
