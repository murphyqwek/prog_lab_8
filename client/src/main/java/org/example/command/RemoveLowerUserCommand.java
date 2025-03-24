package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.fieldReader.MusicBandFieldReader;
import org.example.base.iomanager.IOManager;
import org.example.base.model.MusicBand;
import org.example.base.response.ClientCommandRequest;
import org.example.network.NetworkClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * RemoveLowerUserCommand - класс команды для удаления из коллекции всех элементов, меньшие, чем заданный.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class RemoveLowerUserCommand extends UserCommand {
    private final NetworkClient networkClient;

    /**
     * Конструктор класса
     * @param networkClient класс для работы с коллекцией
     * @param ioManager класс для работы с вводом-выводом
     */
    public RemoveLowerUserCommand(NetworkClient networkClient, IOManager ioManager) {
        super("remove_lower", "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный", ioManager);

        this.networkClient = networkClient;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список аргументов типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public void execute(List<String> args) throws CommandArgumentExcetpion {
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion(getName() + " не принимает никаких однострочных аргументов");
        }

        MusicBand userMusicBand;
        try {
            int id = 0;
            userMusicBand = new MusicBandFieldReader(ioManager).executeMusicBand(id);
        } catch (InterruptedException e) {
            ioManager.writeError("Ввод прерван");
            return;
        }

        List<Serializable> arguments = new ArrayList<>();
        arguments.add(userMusicBand);

        ClientCommandRequest response = new ClientCommandRequest(this.getName(), arguments);

        networkClient.sendUserCommand(response);
        ioManager.writeLine("Отправка команды...");

        printResponse(networkClient);
    }
}
