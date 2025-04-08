package org.example.base.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * NetworkChucnkSender - описание класса.
 *
 * @version 1.0
 */

public class NetworkChucnkUtil {
    private static final int MAX_CHUNK_SIZE = 500; // Максимальный размер чанка (в байтах)

    public static List<byte[]> sendUserCommand(byte[] serializedData) throws IOException {

        List<byte[]> chunks = new ArrayList<byte[]>();
        // Генерируем уникальный ID пакета
        String packetId = UUID.randomUUID().toString();

        // Разбиваем данные на чанки
        int totalChunks = (int) Math.ceil((double) serializedData.length / MAX_CHUNK_SIZE);

        for (int i = 0; i < totalChunks; i++) {
            // Определяем размер текущего чанка
            int start = i * MAX_CHUNK_SIZE;
            int length = Math.min(MAX_CHUNK_SIZE, serializedData.length - start);
            byte[] chunkData = new byte[length];
            System.arraycopy(serializedData, start, chunkData, 0, length);

            // Создаём объект Chunk
            Chunk chunk = new Chunk(packetId, i, totalChunks, chunkData);

            // Сериализуем чанк
            ByteArrayOutputStream chunkBaos = new ByteArrayOutputStream();
            ObjectOutputStream chunkOos = new ObjectOutputStream(chunkBaos);
            chunkOos.writeObject(chunk);
            chunkOos.close();

            byte[] chunkBytes = chunkBaos.toByteArray();
            chunks.add(chunkBytes);
        }

        return chunks;
    }
}