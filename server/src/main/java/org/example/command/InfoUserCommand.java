package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseInfo;
import org.example.base.response.ServerResponseType;
import org.example.manager.CollectionManager;

import java.io.Serializable;
import java.util.List;

/**
 * InfoUserCommand - класс команды для вывода информации о коллекции (тип, дата инициализации, количество элементов и т.д.)
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class InfoUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     */
    public InfoUserCommand(CollectionManager collectionManager) {
        super("info", "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");

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
            throw new CommandArgumentExcetpion("Команда не принимает никаких аргументов");
        }

        var collection = this.collectionManager.getCollection();
        String collectionType = this.collectionManager.getCollectionType();
        String date = this.collectionManager.getCollectionCreationDate().toString();
        int countElements = collection.size();

        String response = String.format("Тип коллекции: %s\nДата создания: %s\nКол-во элементов: %d", collectionType, date, countElements);
        return new ServerResponseInfo(ServerResponseType.SUCCESS, response, collectionType, this.collectionManager.getCollectionCreationDate(), countElements);
    }
}
