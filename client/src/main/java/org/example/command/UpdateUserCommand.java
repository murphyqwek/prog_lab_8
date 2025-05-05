package org.example.command;

import org.example.base.iomanager.EmptyIOManager;
import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponse;
import org.example.exception.CouldnotSendExcpetion;
import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.fieldReader.MusicBandFieldReader;
import org.example.base.iomanager.IOManager;
import org.example.base.model.MusicBand;
import org.example.network.NetworkClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * UpdateUserCommand - класс команды для модифицирования элементов коллекции по id.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class UpdateUserCommand extends NetworkUserCommand {
    public UpdateUserCommand(NetworkClient networkClient) {
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному", new EmptyIOManager(), networkClient);
    }

    /**
     * Конструктор класса
     * @param networkClient класс для работы с коллекцией
     * @param ioManager класс для работы с вводом-выводом
     */
    public UpdateUserCommand(NetworkClient networkClient, IOManager ioManager) {
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному", ioManager, networkClient);
    }

    public ServerResponse appExecute(int id, MusicBand newMusicBand) {
        List<Serializable> arguments = new ArrayList<>();
        arguments.add(id);
        arguments.add(newMusicBand);

        ClientCommandRequest request = new ClientCommandRequest(this.getName(), arguments);

        networkClient.sendUserCommand(request);

        return networkClient.getServerResponse();
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public void execute(List<String> args) throws CommandArgumentExcetpion, CouldnotSendExcpetion {
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
        if(args.size() != 1) {
            throw new CommandArgumentExcetpion(getName() + " принимает только один однострочный аргумент - id элемента");
        }

        int id;
        MusicBand mb;
        try {
            id = Integer.parseInt(args.get(0));
            mb = new MusicBandFieldReader(this.ioManager).executeMusicBand(id);
        }
        catch (NumberFormatException e) {
            ioManager.writeError("Ошибка исполнения команды: id должно быть целым числом");
            return null;
        } catch (InterruptedException e) {
            ioManager.writeError("Ввод прерван");
            return null;
        }

        List<Serializable> arguments = new ArrayList<>();
        arguments.add(id);
        arguments.add(mb);

        ClientCommandRequest response = new ClientCommandRequest(this.getName(), arguments);

        return response;
    }
}
