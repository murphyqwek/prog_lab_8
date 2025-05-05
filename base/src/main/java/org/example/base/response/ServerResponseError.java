package org.example.base.response;

/**
 * ServerResponseError - описание класса.
 *
 * @version 1.0
 */

public class ServerResponseError extends ServerResponse {
    private ServerErrorType serverErrorType;

    public ServerResponseError(ServerResponseType type, String message, ServerErrorType serverErrorType) {
        super(type, message);
        this.serverErrorType = serverErrorType;
    }

    public ServerErrorType getServerErrorType() {
        return serverErrorType;
    }
}
