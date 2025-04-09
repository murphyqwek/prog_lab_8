package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.base.response.ServerResponseWithMusicBandList;
import org.example.manager.CollectionManager;

import java.io.Serializable;
import java.util.List;

/**
 * ShowUserCommand - класс команды для отображения всех элементов коллекции в строковом виде.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class ShowUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для работы с коллекцией
     */
    public ShowUserCommand(CollectionManager collectionManager) {
        super("show", "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public ServerResponse execute(List<Serializable> args, String login) throws CommandArgumentExcetpion {
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion(getName() + " не принимает никаких аргументов");
        }

        var collection = collectionManager.getCollection();

        String builder = "";

        var sortedCollection = collection.stream().sorted().toList();

        return new ServerResponseWithMusicBandList(ServerResponseType.SUCCESS, builder, sortedCollection);
    }
}
