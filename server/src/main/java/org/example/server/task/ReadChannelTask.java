package org.example.server.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ClientCommandRequestWithLoginAndPassword;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.manager.ServerCommandManager;
import org.example.manager.UpdateCollectionManager;
import org.example.manager.UserManager;
import org.example.server.ClientRequestWithIP;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import org.example.server.Server;
import org.example.server.util.DeserializationUtil;
import org.example.server.util.ExtractClientRequestUtil;

/**
 * ReadChannelTask - класс задачи по чтению данных из канала.
 *
 * @version 1.0
 */

public class ReadChannelTask implements Runnable {
    private final DatagramChannel channel;
    private final Logger logger = LogManager.getRootLogger();
    private final Server server;
    private final UserManager userManager;
    private final ServerCommandManager serverCommandManager;
    private final ByteBuffer buffer;
    private final InetSocketAddress socketAddress;
    private final UpdateCollectionManager updateCollectionManager;

    public ReadChannelTask(DatagramChannel channel, ByteBuffer buffer, SocketAddress address, Server server, UserManager userManager, ServerCommandManager serverCommandManager, UpdateCollectionManager updateCollectionManager) {
        this.channel = channel;
        this.server = server;
        this.userManager = userManager;
        this.serverCommandManager = serverCommandManager;
        this.buffer = buffer;
        this.socketAddress = (InetSocketAddress)address;
        this.updateCollectionManager = updateCollectionManager;
    }

    @Override
    public void run() {
        logger.info("Запуск задачи на чтение данных из канала в потоке: "+ Thread.currentThread().getName());

        ClientCommandRequest commandRequest;
        try {
            commandRequest = DeserializationUtil.deserializeClientRequest(buffer);
        } catch (IOException e) {
            logger.error("Не удалось прочитать запрос в потоке " + Thread.currentThread().getName() + ": " + e.getMessage());
            return;
        } catch (ClassNotFoundException e) {
            logger.error("Сервер получил неожиданный ответ");
            return;
        }


        if(commandRequest == null) {
            logger.error(String.format("Пришел запрос от клиента %s, который не удалось прочитать в потоке %s", socketAddress, Thread.currentThread().getName()));
            server.send(channel, socketAddress, ServerResponseType.ERROR, "Не удалось разобрать запрос");
            return;
        }

        if(!(commandRequest instanceof ClientCommandRequestWithLoginAndPassword requestWithLoginAndPassword)) {
            logger.warn("Клиент не отправил свой пароль и логин");

            ServerResponse serverResponse = new ServerResponse(ServerResponseType.ERROR, "Пользователь не отправил логин и пароль");
            server.send(channel, socketAddress, serverResponse);
            return;
        }

        logger.info(String.format("Пришло сообщения от %s в потоке %s:\n%s", socketAddress, Thread.currentThread().getName(), commandRequest));
        logger.info("Передаем задачу обработки запроса другому потоку");

        var executeTask = new ExecuteTask(userManager, server, serverCommandManager, requestWithLoginAndPassword, channel, socketAddress, updateCollectionManager);

        new Thread(executeTask).start();
    }
}
