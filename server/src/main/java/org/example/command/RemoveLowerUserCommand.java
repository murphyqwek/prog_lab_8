package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerErrorType;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseError;
import org.example.base.response.ServerResponseType;
import org.example.database.CollectionDataBaseService;
import org.example.exception.CannotConnectToDataBaseException;
import org.example.exception.CouldnotAddMusicBandToDataBaseExcpetion;
import org.example.manager.CollectionManager;
import org.example.base.model.MusicBand;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;

/**
 * RemoveLowerUserCommand - класс команды для удаления из коллекции всех элементов, меньшие, чем заданный.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class RemoveLowerUserCommand extends UserCommand {
    private final CollectionManager collectionManager;
    private final CollectionDataBaseService collectionDataBaseService;

    /**
     * Конструктор класса
     * @param collectionManager класс для работы с коллекцией
     */
    public RemoveLowerUserCommand(CollectionManager collectionManager, CollectionDataBaseService collectionDataBaseService) {
        super("remove_lower", "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный");

        this.collectionManager = collectionManager;
        this.collectionDataBaseService = collectionDataBaseService;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список аргументов типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public ServerResponse execute(List<Serializable> args, String login) throws CommandArgumentExcetpion {
        if(args.size() != 1) {
            throw new CommandArgumentExcetpion(getName() + " принимает только один аргумент типа MusicBand");
        }

        if(args.get(0) == null || !(args.get(0) instanceof MusicBand userMusicBand)) {
            throw new CommandArgumentExcetpion(getName() + " принимает только один аргумент типа MusicBand");
        }

        int id = collectionManager.generateId();
        userMusicBand.setId(id);

        var collection = collectionManager.getCollection();

        long count = 0;
        var elementsToDelete = collection.stream().filter(mb -> userMusicBand.compareTo(mb) > 0).toList();

        for(var elementToDelete : elementsToDelete) {
            try {
                collectionDataBaseService.deleteMusicBandById(elementToDelete.getId());
                collectionManager.removeMusicBandById(elementToDelete.getId());
                count++;
            } catch (CannotConnectToDataBaseException ex) {
                return new ServerResponseError(ServerResponseType.ERROR, "Внутренняя ошибка сервера - не удалось подключиться к базе данных", ServerErrorType.BD_FALL);
            } catch (Exception e) {
                continue;
            }
        }

        String response = "Успешно. Удалилось: " + count + " объектов";

        return new ServerResponse(ServerResponseType.SUCCESS, response);
    }
}
