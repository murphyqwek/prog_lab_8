package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.manager.CollectionManager;
import org.example.base.model.MusicBand;

import java.io.Serializable;
import java.util.List;

/**
 * UpdateUserCommand - класс команды для модифицирования элементов коллекции по id.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class UpdateUserCommand extends UserCommand {
    private CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для работы с коллекцией
     */
    public UpdateUserCommand(CollectionManager collectionManager) {
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному");

        this.collectionManager = collectionManager;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список аргументов
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public ServerResponse execute(List<Serializable> args) throws CommandArgumentExcetpion {
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

        collectionManager.updateMusicBand(newMusicBand);

        return new ServerResponse(ServerResponseType.SUCCESS, "Элемент с id " + id + " был успешно обновлен");
    }
}
