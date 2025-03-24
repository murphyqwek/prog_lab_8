package org.example.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeoutException;

/**
 * NetworkReceiver - класс для получения сообщения с сервера.
 *
 * @version 1.0
 */

public class NetworkReceiver {
    private static Logger logger = LogManager.getRootLogger();
    /**
     * Метод для чтения буффера с канала
     * @param channel
     * @param responseWait время между опрашиванием в миллисекундах
     * @param maxTimeoutCount максимальное количество опрашиваний до TimeoutExcpetion
     * @return ByteBuffer буффер с полученными данными
     * @throws IOException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public static ByteBuffer receiveBuffer(DatagramChannel channel, long responseWait, int maxTimeoutCount) throws IOException, InterruptedException, TimeoutException {
        logger.info("Ожидание ответа от сервера...");
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        for (int timeoutCount = 0; timeoutCount < maxTimeoutCount; timeoutCount++) {
            byteBuffer.clear();
            SocketAddress from = channel.receive(byteBuffer);

            if (from != null) {
                logger.info("Сообщение от сервера пришло");
                byteBuffer.flip();
                return byteBuffer;
            }
            Thread.sleep(responseWait);
        }

        throw new TimeoutException();
    }
}
