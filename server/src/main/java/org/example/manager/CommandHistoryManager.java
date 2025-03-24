package org.example.manager;

import org.example.command.UserCommand;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CommandHistoryManager - класс для управления историей вызываемых команд
 *
 * @author Starikov Arseny
 * @version 1.0
 */
public class CommandHistoryManager {
    private LinkedList<UserCommand> commandHistory;

    /**
     * Конструктор класса
     */
    public CommandHistoryManager() {
        commandHistory = new LinkedList<>();
    }

    /**
     * Метод для добавления команды в историю
     * @param command выполненная команда
     */
    public void addCommand(UserCommand command) {
        if(command == null) {
            throw new NullPointerException();
        }

        commandHistory.addFirst(command);
    }

    /**
     * Метод для получения истории команд
     * @return неизменяемый список истории команд
     */
    public List<UserCommand> getCommandHistory() {
        return Collections.unmodifiableList(commandHistory);
    }

    /**
     * Метод для получения истории команд
     * @param count количество последних выполненных команд
     * @return список из count последних команд
     */
    public List<UserCommand> getCommandHistory(int count) {
        if (count < 0) {
            throw new IndexOutOfBoundsException("Count должен быть больше нуля");
        }

        var commands = commandHistory.stream().limit(count).toList();

        return commands;
    }

    /**
     * Метод для получения количество исполненных команд
     * @return количество исполненных команд
     */
    public int getCommandHistorySize() {
        return commandHistory.size();
    }
}
