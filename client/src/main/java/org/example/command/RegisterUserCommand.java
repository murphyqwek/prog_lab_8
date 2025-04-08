package org.example.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.UserLoginPasswordContainer;
import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.iomanager.IOManager;
import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponseType;
import org.example.base.response.ServerResponseWithMusicBandList;
import org.example.exception.CouldnotSendExcpetion;
import org.example.exception.ServerErrorResponseExcpetion;
import org.example.network.NetworkClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * RegisterUserCommand - класс команды для регистрации пользователя.
 *
 * @version 1.0
 */

public class RegisterUserCommand extends NetworkUserCommand {
    private final UserLoginPasswordContainer userLoginPasswordContainer;
    private final Logger logger = LogManager.getRootLogger();
    /**
     * Конструктор класса
     *
     * @param ioManager     класс для работы с вводом-выводом
     * @param networkClient
     */
    public RegisterUserCommand(IOManager ioManager, NetworkClient networkClient, UserLoginPasswordContainer userLoginPasswordContainer) {
        super("register", "register - регистрация", ioManager, networkClient);
        this.userLoginPasswordContainer = userLoginPasswordContainer;
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
            throw new CommandArgumentExcetpion(getName() + " не принимает никаких аргументов");
        }

        List<Serializable> arguments = new ArrayList<>();
        arguments.add(userLoginPasswordContainer.getLogin());
        arguments.add(userLoginPasswordContainer.getPassword());

        ClientCommandRequest response = new ClientCommandRequest(this.getName(), arguments);
        return response;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список аругментов типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public void execute(List<String> args) throws CommandArgumentExcetpion, ServerErrorResponseExcpetion {
        var request = getClientCommandRequest(args);

        sendClientCommandResponse(request);
    }

    @Override
    public void sendClientCommandResponse(ClientCommandRequest clientCommandRequest) throws CouldnotSendExcpetion {
        networkClient.sendUserCommand(clientCommandRequest);
        ioManager.writeLine("Отправка команды...");

        var response = networkClient.getServerResponse();

        if(response.getType() == ServerResponseType.FAILURE) {
            throw new ServerErrorResponseExcpetion(response.getMessage(), false);
        }

        logger.info("Регистрация прошла успешно!");
        ioManager.writeLine("Регистрация прошла успешно!");
    }
}
