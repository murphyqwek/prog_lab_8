package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.model.MusicBand;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.manager.CollectionManager;

import java.io.Serializable;
import java.util.List;

/**
 * SumOfAlbumsCountUserCommand - класс команды для вывода суммы значений поля albumsCount для всех элементов коллекции
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class SumOfAlbumsCountUserCommand extends UserCommand {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager класс для управления коллекцией
     */
    public SumOfAlbumsCountUserCommand(CollectionManager collectionManager) {
        super("sum_of_albums_count", "sum_of_albums_count: вывести сумму значений поля albumsCount для всех элементов коллекции");

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
        if (!args.isEmpty()) {
            throw new CommandArgumentExcetpion("Команда не принимает никаких аргументов");
        }

        long result = 0;

        var collection = collectionManager.getCollection();

        result = collection.stream().mapToLong(MusicBand::getAlbumsCount).sum();

        String response = "Суммарное значение поля albumsCount: " + result;
        return new ServerResponse(ServerResponseType.SUCCESS, response);
    }
}
