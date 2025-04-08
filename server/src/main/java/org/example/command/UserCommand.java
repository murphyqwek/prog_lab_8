package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.iomanager.IOManager;
import org.example.base.response.ServerResponse;

import java.io.Serializable;
import java.util.ArrayList;
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

    /**
     * Конструктор класса
     * @param name имя команды
     * @param description описание команды
     */
    public UserCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Метод для выполнения команд
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    public abstract ServerResponse execute(List<Serializable> args, String login) throws CommandArgumentExcetpion;

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

    @Override
    public String toString() {
        return this.name;
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
