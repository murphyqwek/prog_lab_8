package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.manager.UpdateCollectionManager;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * SubscribeServerCommand - описание класса.
 *
 * @version 1.0
 */

public class UnsubscribeServerCommand extends UserCommand {
    private final UpdateCollectionManager updateCollectionManager;
    /**
     * Конструктор класса
     */
    public UnsubscribeServerCommand(UpdateCollectionManager updateCollectionManager) {
        super("unsubscribe", "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.updateCollectionManager = updateCollectionManager;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public ServerResponse execute(List<Serializable> args, String login) throws CommandArgumentExcetpion {
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion(getName() + " не принимает никаких аргументов");
        }

        return new ServerResponse(ServerResponseType.SUCCESS, "");
    }

    public ServerResponse execute(InetSocketAddress address) throws CommandArgumentExcetpion {
        updateCollectionManager.removeAddress(address);

        return new ServerResponse(ServerResponseType.SUCCESS, "");
    }
}
