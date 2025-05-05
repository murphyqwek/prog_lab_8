package org.example.base.response;

/**
 * ServerRepsonseBollean - описание класса.
 *
 * @version 1.0
 */

public class ServerResponseBollean extends ServerResponse {
    private boolean flag;

    public ServerResponseBollean(ServerResponseType type, String message, boolean flag) {
        super(type, message);
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }
}
