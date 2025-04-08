package org.example.server.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ClientCommandRequestWithLoginAndPassword;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.manager.ServerCommandManager;
import org.example.manager.UserManager;
import org.example.server.ClientRequestWithIP;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

import org.example.server.Server;
import org.example.server.util.ExtractClientRequestUtil;

/**
 * ReadChannelTask - класс задачи по чтению данных из канала.
 *
 * @version 1.0
 */

public class ReadChannelTask implements Runnable {
    private final DatagramChannel channel;
    private final Logger logger = LogManager.getLogger();
    private final Server server;
    private final UserManager userManager;
    private final ServerCommandManager serverCommandManager;

    public ReadChannelTask(DatagramChannel channel, Server server, UserManager userManager, ServerCommandManager serverCommandManager) {
        this.channel = channel;
        this.server = server;
        this.userManager = userManager;
        this.serverCommandManager = serverCommandManager;
    }

    @Override
    public void run() {
        logger.info("Запуск задачи на чтение данных из канала в потоке: "+ Thread.currentThread().getName());

        ClientRequestWithIP request;
        try {
            request = ExtractClientRequestUtil.getClientRequest(channel);
        } catch (IOException e) {
            logger.error("Не удалось прочитать запрос в потоке " + Thread.currentThread().getName() + ": " + e.getMessage());
            return;
        }


        if(request.clientResponse() == null) {
            logger.error(String.format("Пришел запрос от клиента %s, который не удалось прочитать в потоке %s", request.ip(), Thread.currentThread().getName()));
            server.send(channel, request.ip(), ServerResponseType.ERROR, "Не удалось разобрать запрос");
            return;
        }

        ClientCommandRequest commandRequest = request.clientResponse();

        if(!(commandRequest instanceof ClientCommandRequestWithLoginAndPassword requestWithLoginAndPassword)) {
            logger.warn("Клиент не отправил свой пароль и логин");

            ServerResponse serverResponse = new ServerResponse(ServerResponseType.ERROR, "Пользователь не отправил логин и пароль");
            server.send(channel, request.ip(), serverResponse);
            return;
        }

        logger.info(String.format("Пришло сообщения от %s в потоке %s:\n%s", request.ip(), Thread.currentThread().getName(), commandRequest));
        logger.info("Передаем задачу обработки запроса другому потоку");

        var executeTask = new ExecuteTask(userManager, server, serverCommandManager, requestWithLoginAndPassword, channel, request.ip());

        new Thread(executeTask).start();
    }
}
