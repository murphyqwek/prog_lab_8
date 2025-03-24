package org.example.server.util;

import org.example.base.response.ServerResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * SerializationUtil - класса-утилита для сериализации ответов сервера.
 *
 * @version 1.0
 */

public class SerializationUtil {
    public static ByteBuffer serializeServerResponse(ServerResponse serverResponse) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(serverResponse);
        oos.close();
        return ByteBuffer.wrap(baos.toByteArray());
    }
}
