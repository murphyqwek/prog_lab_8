package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.database.CollectionDataBaseService;
import org.example.manager.CollectionManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ClearUserCommand - класс команды для очищения коллекции.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class ClearUserCommand extends UserCommand {
    private final CollectionManager collectionManager;
    private final CollectionDataBaseService collectionDataBaseService;

    /**
     * Конструктор класс
     * @param collectionManager класс менеджера коллекции
     */
    public ClearUserCommand(CollectionManager collectionManager, CollectionDataBaseService collectionDataBaseService) {
        super("clear", "clear : очистить коллекцию");
        this.collectionManager = collectionManager;
        this.collectionDataBaseService = collectionDataBaseService;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список аргументов
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public ServerResponse execute(List<Serializable> args, String login) throws CommandArgumentExcetpion {
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion("Команда не принимает никаких аргументов");
        }

        collectionDataBaseService.clearCollection();
        this.collectionManager.clear();

        return new ServerResponse(ServerResponseType.SUCCESS, "Коллекция очищена");
    }
}
