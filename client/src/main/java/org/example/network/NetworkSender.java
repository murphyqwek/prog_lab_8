package org.example.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.response.ClientCommandRequest;
import org.example.exception.SerializationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * NetworkSender - класс для отправки сообщения на сервер.
 *
 * @version 1.0
 */

public class NetworkSender {
    private static final Logger logger = LogManager.getRootLogger();
    /**
     * Метод для отправки команд пользователя на сервер
     * @param clientCommandRequest
     */
    public static void sendUserCommand(ClientCommandRequest clientCommandRequest, DatagramChannel channel) {
        try {
            logger.info("Отправка сообщения на сервер");
            logger.info(clientCommandRequest.toString());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(clientCommandRequest);


            ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            channel.send(buffer, channel.getRemoteAddress());
            logger.info("Сообщение отправлено");
        } catch (IOException e) {
            logger.error("Не удалось сереализовать запрос клиента");
            logger.error(e.getMessage());
            throw new SerializationException(e.getMessage());
        }
    }
}
