package org.example.server.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ClientCommandRequestWithLoginAndPassword;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.command.RegisterServerCommand;
import org.example.exception.CannotConnectToDataBaseException;
import org.example.manager.ServerCommandManager;
import org.example.manager.UserManager;
import org.example.server.Server;
import org.example.server.UserData;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

/**
 * ExecuteTask - класс задачи по исполнению пользовательских команд.
 *
 * @version 1.0
 */

public class ExecuteTask implements Runnable {
    private final UserManager userManager;
    private final Server server;
    private final ServerCommandManager serverCommandManager;
    private final ClientCommandRequestWithLoginAndPassword request;
    private final DatagramChannel channel;
    private final Logger logger = LogManager.getRootLogger();
    private final InetSocketAddress address;

    public ExecuteTask(UserManager userManager, Server server, ServerCommandManager serverCommandManager, ClientCommandRequestWithLoginAndPassword request, DatagramChannel channel, InetSocketAddress address) {
        this.userManager = userManager;
        this.server = server;
        this.serverCommandManager = serverCommandManager;
        this.request = request;
        this.channel = channel;
        this.address = address;
    }

    @Override
    public void run() {
        if(request.getCommandName().equals("register")) {
            ServerResponse response;
            try {
                response = new RegisterServerCommand(userManager, address.getAddress()).execute(request.getArguments(), request.getLogin());
            } catch (CommandArgumentExcetpion ex) {
                response = new ServerResponse(ServerResponseType.ERROR, ex.getMessage());
            }

            server.send(channel, address, response);
            return;
        }

        logger.info(String.format("Пользователь отправил запрос с логином: %s; паролем %s", request.getLogin(), request.getPassword()));

        if(!checkisAuthorized(request.getLogin(), request.getPassword())) {
            return;
        }

        var serverResponse = serverCommandManager.execute(request.getCommandName(), request.getArguments(), request.getLogin());

        if(serverResponse.getType() == ServerResponseType.SUCCESS) {
            logger.info("Сообщение обработано успешно: " + serverResponse);
        }
        else {
            logger.warn("Во время обработки сообщении произошла ошибка: " + serverResponse);
        }

        server.send(channel, address, serverResponse);
    }

    public boolean checkisAuthorized(String login, String password) {
        try {
            if(!userManager.isAuthorized(address.getAddress(), request.getLogin(), request.getPassword())) {
                logger.warn("Не удалось авторизовать пользователя");
                server.send(channel, address, ServerResponseType.UNAUTHORIZED, "Логин и/или пароль неверны");
                return false;
            }

            return true;
        } catch (CannotConnectToDataBaseException e) {
            logger.error("Внутренняя ошибка сервера при работой с базой данных");
            server.send(channel, address, ServerResponseType.ERROR, "Внутренняя ошибка сервера при работой с базой данных");
            return false;
        }
    }

}
