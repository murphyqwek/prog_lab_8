package org.example.base.response;

import java.io.Serializable;

/**
 * ServerResponse - класс для хранения ответа сервера.
 *
 * @version 1.0
 */

public class ServerResponse implements Serializable {
    private final ServerResponseType type;
    private final String message;

    public ServerResponse(ServerResponseType type, String message) {
        this.type = type;
        this.message = message;
    }

    public ServerResponseType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ServerResponse {type: " + type + ", message: " + message + "}";
    }
}
