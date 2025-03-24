package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.fieldReader.MusicBandFieldReader;
import org.example.base.iomanager.IOManager;
import org.example.base.model.MusicBand;
import org.example.base.response.ClientCommandRequest;
import org.example.exception.CouldnotSendExcpetion;
import org.example.network.NetworkClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * AddIfMaxUserCommand - класс команды для добавления нового элемента в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class AddIfMaxUserCommand extends UserCommand {
    NetworkClient networkClient;

    /**
     * Конструктор класса
     * @param ioManager класс для управления ввода-вывода
     */
    public AddIfMaxUserCommand(NetworkClient networkClient, IOManager ioManager) {
        super("add_if_max", "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции", ioManager);
        this.networkClient = networkClient;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public void execute(List<String> args) throws CommandArgumentExcetpion, CouldnotSendExcpetion {
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion(getName() + " не принимает однострочных аргументов");
        }

        MusicBand userMusicBand;
        try {
            int id = 1;
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
