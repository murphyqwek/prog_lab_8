package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.exception.ElementNotFoundException;
import org.example.base.response.ServerErrorType;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseError;
import org.example.base.response.ServerResponseType;
import org.example.database.CollectionDataBaseService;
import org.example.exception.CannotConnectToDataBaseException;
import org.example.exception.CannotDeleteFromDataBaseException;
import org.example.manager.CollectionManager;
import org.example.util.hash.SendUpdates;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * RemoveByIdCommand - класс команды для удаления элемента из коллекции по его id
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class RemoveByIdCommand extends UserCommand {
    private CollectionManager collectionManager;
    private CollectionDataBaseService collectionDataBaseService;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     */
    public RemoveByIdCommand(CollectionManager collectionManager, CollectionDataBaseService collectionDataBaseService) {
        super("remove_by_id", "remove_by_id id : удалить элемент из коллекции по его id");

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
        if(args.size() != 1) {
            throw new CommandArgumentExcetpion("Неверное количество аргументов");
        }

        int id;
        try {
            id = (Integer) args.get(0);
        }
        catch (NumberFormatException e) {
            throw new CommandArgumentExcetpion("id должно быть числом");
        }

        if(!collectionManager.checkOwner(id, login)) {
            return new ServerResponseError(ServerResponseType.FAILURE, "У вас нет прав на удаление этого объекта", ServerErrorType.UNAUTHORIZED);
        }

        try {
            collectionDataBaseService.deleteMusicBandById(id);
            collectionManager.removeMusicBandById(id);
        } catch (CannotConnectToDataBaseException e) {
            return new ServerResponseError(ServerResponseType.ERROR, "Внутрення ошибка сервера - не удалось подключиться к базе данных", ServerErrorType.BD_FALL);
        } catch (CannotDeleteFromDataBaseException e) {
            return new ServerResponseError(ServerResponseType.ERROR, "Не удалось удалить элемент из коллекции", ServerErrorType.CANNOT_DELETE);
        } catch (ElementNotFoundException e) {
            return new ServerResponseError(ServerResponseType.ERROR, "MusicBand с id " + id + " не найден в коллекции", ServerErrorType.DID_NOT_FIND_ELEMENT);
        }

        String response = String.format("MusicBand с id %d успешно удален из коллекции", id);

        SendUpdates.sendUpdates();

        return new ServerResponse(ServerResponseType.SUCCESS, response);
    }
}
