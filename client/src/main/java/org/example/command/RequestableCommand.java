package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ClientCommandRequest;

import java.util.List;

public interface RequestableCommand {
    /**
     * Метод для формирования клиентского запроса
     * @return
     */
    ClientCommandRequest getClientCommandRequest(List<String> args) throws CommandArgumentExcetpion;
}
