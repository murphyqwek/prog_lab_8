package org.example.base.network;

import java.io.Serializable;

/**
 * Chunk - описание класса.
 *
 * @version 1.0
 */

public class Chunk implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String packetId; // Уникальный ID пакета (для различения разных сообщений)
    private final int chunkIndex; // Номер текущей части (начинается с 0)
    private final int totalChunks; // Общее количество частей
    private final byte[] data; // Данные этой части

    public Chunk(String packetId, int chunkIndex, int totalChunks, byte[] data) {
        this.packetId = packetId;
        this.chunkIndex = chunkIndex;
        this.totalChunks = totalChunks;
        this.data = data;
    }

    public String getPacketId() {
        return packetId;
    }

    public int getChunkIndex() {
        return chunkIndex;
    }

    public int getTotalChunks() {
        return totalChunks;
    }

    public byte[] getData() {
        return data;
    }
}
