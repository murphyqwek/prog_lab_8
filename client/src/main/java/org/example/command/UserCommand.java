package org.example.command;

import org.example.base.iomanager.IOManager;
import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponseType;
import org.example.exception.CouldnotSendExcpetion;
import org.example.exception.ServerErrorResponseExcpetion;
import org.example.network.NetworkClient;

import java.util.List;

/**
 * UserCommand - абстрактный класс команды, которые может вызывать пользователь
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public abstract class UserCommand implements Comparable<UserCommand> {
    private final String name;
    private final String description;
    protected IOManager ioManager;

    /**
     * Конструктор класса
     * @param name имя команды
     * @param description описание команды
     * @param ioManager класс для работы с вводом-выводом
     */
    public UserCommand(String name, String description, IOManager ioManager) {
        this.name = name;
        this.description = description;
        this.ioManager = ioManager;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список аругментов типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    public abstract void execute(List<String> args) throws CommandArgumentExcetpion, ServerErrorResponseExcpetion;

    /**
     * Метод для установки нового IOManager
     * @param ioManager новый IOManager
     */
    public void setIOManager(IOManager ioManager) {
        this.ioManager = ioManager;
    }

    /**
     * Метод для получения IOManager
     * @return IOManager ioManager
     */
    public IOManager getIOManager() {
        return ioManager;
    }

    /**
     * Метод для получения имени команды
     * @return имя команды
     */
    public String getName() {
        return this.name;
    }

    /**
     * Метод для получения описания команды
     * @return описание
     */
    public String getDescription() {
        return this.description;
    }


    /**
     * Метод для сравнения двух команд. Сравнение происходит по имени
     * @param o the object to be compared.
     */
    @Override
    public int compareTo(UserCommand o) {
        return this.name.compareTo(o.name);
    }
}

