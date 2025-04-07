package org.example.command;

import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.iomanager.IOManager;
import org.example.base.response.ClientCommandRequest;
import org.example.exception.CouldnotSendExcpetion;
import org.example.network.NetworkClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ExitCommand - класс команды для выхода из программы без сохранения в файл.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class ExitCommand extends UserCommand{

    /**
     * Конструктор класса
     * @param ioManager класс для работы с вводом-выводом
     */
    public ExitCommand(IOManager ioManager) {
        super("exit", "exit: завершить программу (без сохранения в файл)", ioManager);
    }


    /**
     * Метод для выполнения команд
     *
     * @param args список аргументов типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public void execute(List<String> args) throws CommandArgumentExcetpion {
        if(!args.isEmpty()) {
            throw new CommandArgumentExcetpion(getName() + " не принимает никаких аргументов");
        }
        ioManager.writeLine("Завершение программы");
        System.exit(0);
    }
}
