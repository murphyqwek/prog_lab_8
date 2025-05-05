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
import java.util.Date;
import java.util.List;

/**
 * AddUserCommand - класс команды для добавления нового элемента в коллекцию.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class AddUserCommand extends UserCommand {
    private final CollectionManager collectionManager;
    private final CollectionDataBaseService collectionDataBaseService;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     */
    public AddUserCommand(CollectionManager collectionManager, CollectionDataBaseService collectionDataBaseService) {
        super("add", "add {element}: добавить новый элемент в коллекцию");

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

        if(args.get(0) == null || !(args.get(0) instanceof MusicBand newMusicBand)) {
            throw new CommandArgumentExcetpion("Неверный тип аргумента");
        }

        int id = collectionManager.generateId();
        newMusicBand.setId(id);
        newMusicBand.setCreationDate(new Date());

        if(!newMusicBand.isValid()) {
            return new ServerResponse(ServerResponseType.CORRUPTED, "Данные повреждены");
        }

        String responseMessage = "";

        try {
            collectionDataBaseService.addNewMusicBand(newMusicBand, login);
        } catch (CannotConnectToDataBaseException e) {
            responseMessage = "Внутрення ошибка с базой данных";
            return new ServerResponseError(ServerResponseType.ERROR, responseMessage, ServerErrorType.BD_FALL);
        } catch (CouldnotAddMusicBandToDataBaseExcpetion e) {
            responseMessage = "Не удалось добавить элемент в базу данных";
            return new ServerResponseError(ServerResponseType.ERROR, responseMessage, ServerErrorType.CANNOT_ADD_TO_BD);
        }

        collectionManager.addNewMusicBand(newMusicBand, login);

        return new ServerResponse(ServerResponseType.SUCCESS, "Группа успешно добавлена в коллекцию");
    }
}
