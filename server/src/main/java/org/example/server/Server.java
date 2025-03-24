package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.manager.ServerCommandManager;
import org.example.server.util.DeserializationUtil;
import org.example.server.util.SerializationUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.nio.ByteBuffer;

/**
 * Server - класс, в котором происходит вся серверная логика.
 *
 * @version 1.0
 */

public class Server {
    private final Logger logger = LogManager.getRootLogger();
    private final ServerCommandManager commandManager;
    private final int port;

    private final Selector selector;

    /**
     * Конструктор класса
     * @param commandManager серверный менеджер команд, в котором загружены все команды
     * @param port порт, на котором работает сервер
     * @throws IOException
     */
    public Server(ServerCommandManager commandManager, int port) throws IOException {
        this.commandManager = commandManager;
        this.port = port;

        this.selector = Selector.open();

        DatagramChannel channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress("0.0.0.0", this.port));
        channel.configureBlocking(false);

        channel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * Бесконечный рабочий цикл сервера
     * @throws IOException
     */
    public void cycle() throws IOException {
        while (true) {
            selector.select();

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                if (key.isReadable()) {
                    DatagramChannel channel = (DatagramChannel) key.channel();

                    ClientRequestWithIP request = getClientRequest(channel);
                    if(request.clientResponse() == null) {
                        logger.error(String.format("Пришел запрос от клиента %s, который не удалось обработать", request.ip()));
                        send(channel, request.ip(), ServerResponseType.ERROR, "Не удалось разобрать запрос");
                        continue;
                    }

                    ClientCommandRequest commandRequest = request.clientResponse();

                    logger.info(String.format("Пришло сообщения от %s:\n%s", request.ip(), commandRequest));

                    var serverResponse = commandManager.execute(commandRequest.getCommandName(), commandRequest.getArguments());

                    if(serverResponse.getType() == ServerResponseType.SUCCESS) {
                        logger.info("Сообщение обработано успешно: " + serverResponse);
                    }
                    else {
                        logger.warn("Во время обработки сообщении произошла ошибка: " + serverResponse);
                    }

                    try {
                        send(channel, request.ip(), serverResponse);
                    } catch (IOException e) {
                        logger.error("Ошибка при отправке сообщения: " + e.getMessage());
                    }
                    //channel.close();
                }
            }
        }
    }

    /**
     * Метод для получения данных о клиентском запросе
     * @param channel
     * @return
     * @throws IOException
     */
    private ClientRequestWithIP getClientRequest(DatagramChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        InetSocketAddress from = (InetSocketAddress) channel.receive(buffer);

        buffer.flip();

        ClientCommandRequest request;
        try {
            request = DeserializationUtil.deserializeClientRequest(buffer);
        } catch (ClassNotFoundException ex) {
            return new ClientRequestWithIP(null, from);
        }

        return new ClientRequestWithIP(request, from);
    }

    /**
     * Метод для отправки сообщений клиенту
     * @param channel
     * @param toSend
     * @param responseType
     * @param responeMessage
     * @throws IOException
     */
    public void send(DatagramChannel channel, InetSocketAddress toSend, ServerResponseType responseType, String responeMessage) throws IOException {
        ServerResponse response = new ServerResponse(responseType, responeMessage);
        send(channel, toSend, response);
    }

    /**
     * Метод для отправки сообщений клиенту
     * @param channel
     * @param toSend
     * @param response
     * @throws IOException
     */
    public void send(DatagramChannel channel, InetSocketAddress toSend, ServerResponse response) throws IOException {
        var responseSerialized = SerializationUtil.serializeServerResponse(response);
        channel.send(responseSerialized, toSend);
        logger.info("Сообщение отправлено " + toSend);
    }

}
