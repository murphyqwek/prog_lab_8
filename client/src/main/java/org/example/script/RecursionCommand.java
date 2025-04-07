package org.example.script;

import org.example.command.UserCommand;

import java.util.List;

/**
 * RecursionCommand - описание класса.
 *
 * @version 1.0
 */

public class RecursionCommand implements RecursionExecutable {
    private final UserCommand command;
    private final List<String> args;

    public RecursionCommand(UserCommand command, List<String> args) {
        this.command = command;
        this.args = args;
    }

    @Override
    public void execute() {
        command.execute(args);
    }
}
