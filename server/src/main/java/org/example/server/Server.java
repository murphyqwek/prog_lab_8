package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.parser.CsvCollectionManager;
import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.database.CollectionDataBaseService;
import org.example.database.UserDataBaseService;
import org.example.manager.ServerCommandManager;
import org.example.manager.UserManager;
import org.example.server.task.ReadChannelTask;
import org.example.server.util.DeserializationUtil;
import org.example.server.util.SerializationUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.nio.ByteBuffer;

import java.util.function.Consumer;

/**
 * Server - класс, в котором происходит вся серверная логика.
 *
 * @version 1.0
 */

public class Server {
    private final Logger logger = LogManager.getRootLogger();
    private final ServerCommandManager commandManager;
    private final int port;
    private final DatagramChannel channel;
    private final Selector selector;
    private final UserManager userManager;

    /**
     * Конструктор класса
     * @param commandManager серверный менеджер команд, в котором загружены все команды
     * @param port порт, на котором работает сервер
     * @throws IOException
     */
    public Server(ServerCommandManager commandManager, int port, UserManager userManager) throws IOException {
        this.commandManager = commandManager;
        this.port = port;
        this.selector = Selector.open();

        DatagramChannel channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress("0.0.0.0", this.port));
        channel.configureBlocking(false);

        channel.register(selector, SelectionKey.OP_READ);

        this.channel = channel;

        this.userManager = userManager;
    }

    public void shutdown() {
        userManager.shutdown();
    }

    public void cycle() throws IOException {
        while (true) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(65536); // Размер буфера
                SocketAddress clientAddress = channel.receive(buffer); // Блокирующий вызов

                if (clientAddress != null) {
                    buffer.flip();
                    logger.info("Получены данные от {}", clientAddress);

                    ReadChannelTask readChannelTask = new ReadChannelTask(channel, buffer, clientAddress, this, userManager, commandManager);

                    new Thread(readChannelTask).start();
                }

            } catch (IOException e) {
                logger.error("Ошибка в основном цикле сервера: {}", e.getMessage());
            }
        }
    }
    
    /**
     * Метод для отправки сообщений клиенту
     * @param channel
     * @param toSend
     * @param responseType
     * @param responseMessage
     * @throws IOException
     */
    public void send(DatagramChannel channel, InetSocketAddress toSend, ServerResponseType responseType, String responseMessage){
        ServerResponse response = new ServerResponse(responseType, responseMessage);
        send(channel, toSend, response);
    }

    /**
     * Метод для отправки сообщений клиенту
     * @param channel
     * @param toSend
     * @param response
     * @throws IOException
     */
    public void send(DatagramChannel channel, InetSocketAddress toSend, ServerResponse response)  {
        new Thread(() -> {
            try {
                var responseSerialized = SerializationUtil.serializeServerResponse(response);
                channel.send(responseSerialized, toSend);
                logger.info("Сообщение отправлено " + toSend);
            } catch (IOException e) {
                logger.error("Не удалось отправить сообщение " + toSend.getAddress().toString());
                logger.error(e.getMessage());
            }
        }).start();
    }

}
