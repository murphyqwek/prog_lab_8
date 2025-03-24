package org.example.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.*;
import org.example.command.UserCommand;
import org.example.exception.ServerErrorResponseExcpetion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ClientCommandManager - класс менеджера команд пользователя
 *
 * @version 1.0
 */

public class ClientCommandManager {
    private final Logger logger = LogManager.getRootLogger();
    private HashMap<String, UserCommand> commands;

    /**
     * Конструктор класса
     */
    public ClientCommandManager() {
        this.commands = new HashMap<>();
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
     * @param input строка с именем команды и её аргументами
     * @throws UnknownCommandException если не удалось определить команду
     * @throws ServerErrorResponseExcpetion если возникла ошибка с сервером
     */
    public void execute(String input) throws ServerErrorResponseExcpetion {
        var arguments = parsingInputCommand(input);

        String commandName = arguments.get(0);
        arguments.remove(0);

        UserCommand executingCommand = this.commands.get(commandName);
        if(executingCommand == null) {
            System.err.println("Неопознанная команда: " + commandName);
            logger.error("Неопознанная команда: " + commandName);
            return;
        }

        try {
            logger.info("Вызов команды " + commandName);
            executingCommand.execute(arguments);
        }
        catch (CommandArgumentExcetpion e) {
            System.err.println("ОШИБКА исполнения команды : " + e.getMessage());
            logger.error("ОШИБКА исполнения команды : " + e.getMessage());
        }
    }

    /**
     * Метод для парсинга входного значения
     * @param input входное значение
     * @return список, где первый элемент - имя команды, а остальные - аргументы к этой команде
     */
    private ArrayList<String> parsingInputCommand(String input) {
        input = input.trim();

        ArrayList<String> commands = new ArrayList<>();
        for(var part : input.split(" ")) {
            commands.add(part.trim());
        }

        return commands;
    }

    /**
     * Метод для очищения списка команд
     */
    public void clearCommands() {
        this.commands.clear();
    }

}
