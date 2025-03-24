package org.example.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.iomanager.IOManager;
import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponseWithMusicBandList;
import org.example.exception.ServerErrorResponseExcpetion;
import org.example.network.NetworkClient;

import java.util.Collections;
import java.util.List;

/**
 * ShowUserCommand - класс команды для получения списка элементов коллекции.
 *
 * @version 1.0
 */

public class ShowUserCommand extends UserCommand {
    private final NetworkClient networkClient;
    private final Logger logger = LogManager.getLogger();

    /**
     * Конструктор класса
     *
     * @param ioManager   класс для работы с вводом-выводом
     */
    public ShowUserCommand(IOManager ioManager, NetworkClient networkClient) {
        super("show", "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении", ioManager);
        this.networkClient = networkClient;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список аргументов
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public void execute(List<String> args) throws CommandArgumentExcetpion, ServerErrorResponseExcpetion {
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion("Команда не принимает никаких аргументов");
        }

        ClientCommandRequest clientCommandRequest = new ClientCommandRequest(this.getName(), Collections.emptyList());

        networkClient.sendUserCommand(clientCommandRequest);
        ioManager.writeLine("Отправка команды...");

        var response = networkClient.getServerResponse();

        if(!(response instanceof ServerResponseWithMusicBandList responseWithList)) {
            throw new ServerErrorResponseExcpetion("Сервер вернул не тот тип данных, который ожидался", true);
        }

        if(responseWithList.getMusicBandList().isEmpty()) {
            ioManager.writeLine("Коллекция пуста");
            return;
        }

        if(responseWithList.getMusicBandList().stream().anyMatch(mb -> !mb.isValid())) {
            logger.warn("Сообщение пришло поврежденным");
            throw new ServerErrorResponseExcpetion("Сообщение пришло поврежденным", false);
        }

        responseWithList.getMusicBandList().stream().forEach(mb -> ioManager.writeLine(mb));
    }
}
