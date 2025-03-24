package org.example.server.util;

import org.example.base.response.ClientCommandRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

/**
 * DeserializationUtil класс-утилита для десериализации клиентских запросов.
 *
 * @version 1.0
 */

public class DeserializationUtil {
    public static ClientCommandRequest deserializeClientRequest(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), buffer.position(), buffer.limit());
        ObjectInputStream ois = new ObjectInputStream(bais);
        ClientCommandRequest clientCommandRequest = (ClientCommandRequest) ois.readObject();
        ois.close();
        return clientCommandRequest;
    }
}
