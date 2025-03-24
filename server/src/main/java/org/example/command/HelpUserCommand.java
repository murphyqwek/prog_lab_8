package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.manager.ServerCommandManager;

import java.io.Serializable;
import java.util.List;

/**
 * HelpCommand - класс команды для вывода справки по доступным командам
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class HelpUserCommand extends UserCommand {
    private final ServerCommandManager commandManager;

    /**
     * Конструктор класса
     * @param commandManager класс для управления командами
     */
    public HelpUserCommand(ServerCommandManager commandManager) {
        super("help", "help: вывести справку по доступным командам");

        this.commandManager = commandManager;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список аргументов
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public ServerResponse execute(List<Serializable> args) throws CommandArgumentExcetpion {
        if (!args.isEmpty()) {
            throw new CommandArgumentExcetpion("Команда не принимает никаких аргументов");
        }

        StringBuilder builder = new StringBuilder();
        commandManager.getCommands().stream().sorted().forEach(command -> builder.append(command.getDescription()).append("\n"));

        String response = builder.toString();

        return new ServerResponse(ServerResponseType.SUCCESS, response);
    }
}
