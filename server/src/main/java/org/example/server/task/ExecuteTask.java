package org.example.server.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.response.ClientCommandRequestWithLoginAndPassword;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.exception.CannotConnectToDataBaseException;
import org.example.manager.ServerCommandManager;
import org.example.manager.UserManager;
import org.example.server.Server;
import org.example.server.UserData;

import java.io.IOException;
import java.net.InetAddress;
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
            logger.info("Пользователь хочет зарегистрироваться");
            register(request.getLogin(), request.getPassword());
            return;
        }

        if(!checkisAuthorized(request.getLogin(), request.getPassword())) {
            return;
        }

        //TODO: вызов commandMenager'а
    }

    public boolean checkisAuthorized(String login, String password) {
        try {
            if(!userManager.isAuthorized(address.getAddress(), request.getLogin(), request.getPassword())) {
                server.send(channel, address, ServerResponseType.UNAUTHORIZED, "Логин и/или пароль неверны");
                return false;
            }

            return true;
        } catch (CannotConnectToDataBaseException e) {
            server.send(channel, address, ServerResponseType.ERROR, "Внутренняя ошибка сервера при работой с базой данных");
            return false;
        }
    }

    public void register(String login, String password) {
        try {
            ServerResponse response;
            if(userManager.registerNewUser(new UserData(login, password, address.getAddress()))) {
                response = new ServerResponse(ServerResponseType.SUCCESS, "Вы успешно зарегистрированы");
            }
            else {
                response = new ServerResponse(ServerResponseType.FAILURE, "Ваш логин не уникален");
            }
            server.send(channel, address, response);
        } catch (CannotConnectToDataBaseException e) {
            server.send(channel, address, ServerResponseType.ERROR, "Ошибка соединения с базой данных");
        }
    }

}
