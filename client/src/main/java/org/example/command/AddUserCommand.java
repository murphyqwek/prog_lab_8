package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.fieldReader.MusicBandFieldReader;
import org.example.base.model.MusicBand;
import org.example.base.response.ClientCommandRequest;
import org.example.network.NetworkClient;
import org.example.base.iomanager.IOManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * AddUserCommand - класс команды для добавления нового элемента в коллекцию.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class AddUserCommand extends NetworkUserCommand{
    /**
     * Конструктор класса
     * @param networkClient класс для управления коллекцией
     * @param ioManager класс для работы с вводом-выводом
     */
    public AddUserCommand(NetworkClient networkClient, IOManager ioManager) {
        super("add", "add {element}: добавить новый элемент в коллекцию", ioManager, networkClient);
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public void execute(List<String> args) throws CommandArgumentExcetpion {
        var clientResponse = getClientCommandRequest(args);

        if(clientResponse == null) {
            return;
        }

        sendClientCommandResponse(clientResponse);
    }

    /**
     * Метод для формирования клиентского запроса
     *
     * @param args
     * @return
     */
    @Override
    public ClientCommandRequest getClientCommandRequest(List<String> args) throws CommandArgumentExcetpion {
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion(getName() + " не принимает никаких однострочных аргументов");
        }

        MusicBand userMusicBand;
        try {
            int id = 1;
            userMusicBand = new MusicBandFieldReader(this.ioManager).executeMusicBand(id);
        } catch (InterruptedException e) {
            ioManager.writeError("Прерывание ввода");
            return null;
        }

        List<Serializable> arguments = new ArrayList<>();
        arguments.add(userMusicBand);

        ClientCommandRequest response = new ClientCommandRequest(this.getName(), arguments);

        return response;
    }
}
