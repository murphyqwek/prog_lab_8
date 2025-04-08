package org.example.server.util;

import org.example.base.response.ClientCommandRequest;
import org.example.server.ClientRequestWithIP;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * ExtractClientRequestUtil - описание класса.
 *
 * @version 1.0
 */

public class ExtractClientRequestUtil {
    /**
     * Метод для получения данных о клиентском запросе
     * @param channel
     * @return
     * @throws IOException
     */
    public static ClientRequestWithIP getClientRequest(DatagramChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        InetSocketAddress from = (InetSocketAddress) channel.receive(buffer);

        if(from == null)

            buffer.flip();

        ClientCommandRequest request;
        try {
            request = DeserializationUtil.deserializeClientRequest(buffer);
        } catch (ClassNotFoundException ex) {
            return new ClientRequestWithIP(null, from);
        }

        return new ClientRequestWithIP(request, from);
    }
}
