package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.database.CollectionDataBaseService;
import org.example.exception.CannotConnectToDataBaseException;
import org.example.exception.CouldnotAddMusicBandToDataBaseExcpetion;
import org.example.manager.CollectionManager;
import org.example.base.model.MusicBand;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * AddIfMaxUserCommand - класс команды для добавления нового элемента в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class AddIfMaxUserCommand extends UserCommand {
    private final CollectionManager collectionManager;
    private final CollectionDataBaseService collectionDataBaseService;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     */
    public AddIfMaxUserCommand(CollectionManager collectionManager, CollectionDataBaseService collectionDataBaseService) {
        super("add_if_max", "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
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
        String responseMessage = "";

        if(args.size() != 1) {
            throw new CommandArgumentExcetpion("Неверное количество аргументов");
        }

        if(args.get(0) == null || !(args.get(0) instanceof MusicBand newMusicBand)) {
            throw new CommandArgumentExcetpion("Команда принимает только MusicBand");
        }

        int id = collectionManager.generateId();
        newMusicBand.setId(id);
        newMusicBand.setCreationDate(new Date());

        if(!newMusicBand.isValid()) {
            return new ServerResponse(ServerResponseType.CORRUPTED, "Данные повреждены");
        }

        MusicBand maxElement = collectionManager.getCollection().stream().max(MusicBand::compareTo).get();
        if(maxElement.compareTo(newMusicBand) >= 0) {
            responseMessage = "Ваша группа не превышает наибольшего элемента в коллекции, поэтому она не будет туда добавлена";
            return new ServerResponse(ServerResponseType.SUCCESS, responseMessage);
        }

        responseMessage = "Ваша группа больше наибольшего элемента в коллекции. Добавляем её в коллекцию\nГруппа успешно добавлена в коллекцию";

        try {
            collectionDataBaseService.addNewMusicBand(newMusicBand, login);
        } catch (CannotConnectToDataBaseException e) {
            responseMessage = "Внутрення ошибка с базой данных";
            return new ServerResponse(ServerResponseType.ERROR, responseMessage);
        } catch (CouldnotAddMusicBandToDataBaseExcpetion e) {
            responseMessage = "Не удалось добавить элемент в базу данных";
            return new ServerResponse(ServerResponseType.ERROR, responseMessage);
        }

        collectionManager.addNewMusicBand(newMusicBand, login);

        return new ServerResponse(ServerResponseType.SUCCESS, responseMessage);
    }
}
