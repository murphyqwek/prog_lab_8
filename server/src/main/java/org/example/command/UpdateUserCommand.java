package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.database.CollectionDataBaseService;
import org.example.exception.CannotConnectToDataBaseException;
import org.example.exception.CannotUpdateMusicBandException;
import org.example.manager.CollectionManager;
import org.example.base.model.MusicBand;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * UpdateUserCommand - класс команды для модифицирования элементов коллекции по id.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class UpdateUserCommand extends UserCommand {
    private CollectionManager collectionManager;
    private final CollectionDataBaseService collectionDataBaseService;

    /**
     * Конструктор класса
     * @param collectionManager класс для работы с коллекцией
     */
    public UpdateUserCommand(CollectionManager collectionManager, CollectionDataBaseService collectionDataBaseService) {
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному");

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
        if(args.size() != 2) {
            throw new CommandArgumentExcetpion(getName() + " принимает только два аргумента - id элемента и сам элемент");
        }

        if(args.get(0) == null || !(args.get(0) instanceof Integer id)) {
            throw new CommandArgumentExcetpion(getName() + " первый аргумент должен быть типа Integer");
        }

        if(args.get(1) == null || !(args.get(1) instanceof MusicBand newMusicBand)) {
            throw new CommandArgumentExcetpion(getName() + " второй аргумент должен быть типа MusicBand");
        }

        if(!collectionManager.containsId(id)) {
            return new ServerResponse(ServerResponseType.ERROR, "В коллекции не элемента с id = " + id);
        }

        if(!newMusicBand.isValid()) {
            return new ServerResponse(ServerResponseType.CORRUPTED, "Данные повреждены");
        }

        if(!collectionManager.checkOwner(id, login)) {
            return new ServerResponse(ServerResponseType.FAILURE, "Элемент коллекции не принадлежит пользователю");
        }

        try {
            collectionDataBaseService.updateMusicBand(newMusicBand);
            collectionManager.updateMusicBand(newMusicBand);
        } catch (CannotConnectToDataBaseException e) {
            return new ServerResponse(ServerResponseType.ERROR, "Внутрення ошибка сервера - не удалось подключиться к базе данных");
        } catch (CannotUpdateMusicBandException e) {
            return new ServerResponse(ServerResponseType.ERROR, "Не удалось обновить элемент коллекции");
        }

        return new ServerResponse(ServerResponseType.SUCCESS, "Элемент с id " + id + " был успешно обновлен");
    }
}
