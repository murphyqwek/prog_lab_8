package org.example.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.exception.UnknownCommandException;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.command.UserCommand;

import java.io.Serializable;
import java.util.*;

/**
 * ServerCommandManager - класс менеджера команд пользователя
 *
 * @version 1.0
 */

public class ServerCommandManager {
    private final Logger logger = LogManager.getLogger();
    private final HashMap<String, UserCommand> commands;
    private final CommandHistoryManager commandHistoryManager;
    /**
     * Конструктор класса
     */
    public ServerCommandManager() {
        this.commands = new HashMap<>();
        this.commandHistoryManager = new CommandHistoryManager();
    }

    /**
     * Метод для добавления новой команды
     * @param command новая исполняемая команда
     */
    public void addCommand(UserCommand command) {
        addCommand(command.getName(), command);
    }

    /**
     * Метод для добавления новой команды
     * @param name имя команды, по которой будет происходить её вызов
     * @param command новая исполняемая команда
     */
    public void addCommand(String name, UserCommand command) {
        this.commands.put(name, command);
    }

    /**
     * Метод для исполнения команды
     * @param commandName имя команды
     * @param argument аргументы к команде
     * @throws UnknownCommandException если не удалось определить команду
     */
    public ServerResponse execute(String commandName, List<Serializable> argument){
        UserCommand executingCommand = this.commands.get(commandName);
        if(executingCommand == null) {
            logger.warn("Неопознанная команда " + commandName);
            return new ServerResponse(ServerResponseType.ERROR, "Неопознанная команда");
        }

        try {
            logger.info("Вызов команды " + commandName);
            commandHistoryManager.addCommand(executingCommand);
            return executingCommand.execute(argument);
        }
        catch (CommandArgumentExcetpion e) {
            logger.error(String.format("ОШИБКА исполнения команды %s: %s", commandName, e.getMessage()));
            return new ServerResponse(ServerResponseType.ERROR, "ОШИБКА исполнения команды : " + e.getMessage());
        }
    }

    public Collection<UserCommand> getCommands() {
        return Collections.unmodifiableCollection(this.commands.values());
    }

    public CommandHistoryManager getCommandHistoryManager() {
        return this.commandHistoryManager;
    }
}
