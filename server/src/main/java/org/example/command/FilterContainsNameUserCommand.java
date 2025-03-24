package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.base.response.ServerResponseWithMusicBandList;
import org.example.manager.CollectionManager;

import java.io.Serializable;
import java.util.List;

/**
 * FilterContainsNameUserCommand - класс команды для нахождения элементов, у которых поле name содержит заданную подстроку
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class FilterContainsNameUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     */
    public FilterContainsNameUserCommand(CollectionManager collectionManager) {
        super("filter_contains_name", "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку");

        this.collectionManager = collectionManager;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public ServerResponse execute(List<Serializable> args) throws CommandArgumentExcetpion {
        if(args.size() != 1) {
            throw new CommandArgumentExcetpion("Неверное количество аргументов");
        }

        if(args.get(0) == null || !(args.get(0) instanceof String argument)) {
            throw new CommandArgumentExcetpion("Неверный тип аргумента, " + this.getName() + " ожидает только String");
        }

        if(argument.isEmpty()) {
            throw new CommandArgumentExcetpion("Аргумент не может быть пустой строкой");
        }

        var collection = this.collectionManager.getCollection();

        var selectedCollection = collection.stream().filter(mb -> mb.getName().contains(argument)).sorted().toList();

        return new ServerResponseWithMusicBandList(ServerResponseType.SUCCESS, "", selectedCollection);
    }
}
