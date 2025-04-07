package org.example.script;

import org.example.base.response.ClientCommandRequest;
import org.example.command.NetworkUserCommand;
import org.example.command.UserCommand;

import java.util.List;

/**
 * RecursionCommandWithRequest - описание класса.
 *
 * @version 1.0
 */

public class RecursionCommandWithRequest implements RecursionExecutable {
    private final NetworkUserCommand command;
    private final ClientCommandRequest request;

    public RecursionCommandWithRequest(NetworkUserCommand command, ClientCommandRequest request) {
        this.command = command;
        this.request = request;
    }

    @Override
    public void execute() {
        command.sendClientCommandResponse(request);
    }
}
