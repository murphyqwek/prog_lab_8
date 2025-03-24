package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.manager.CollectionManager;
import org.example.server.Server;

import java.io.Serializable;
import java.util.List;


/**
 * PrintAscendingUserCommand - класс команды для вывода элементов коллекции в порядке возрастания
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class PrintAscendingUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     */
    public PrintAscendingUserCommand(CollectionManager collectionManager) {
        super("print_ascending", "print_ascending : вывести элементы коллекции в порядке возрастания");

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
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion("Команда не принимает никаких аргументов");
        }

        var collection = collectionManager.getCollection();

        //Collections.reverse(collection);

        StringBuilder builder = new StringBuilder();

        collection.stream().sorted().forEach(mb -> builder.append(mb.toString()).append("\n"));

        return new ServerResponse(ServerResponseType.SUCCESS, builder.toString());
    }
}
