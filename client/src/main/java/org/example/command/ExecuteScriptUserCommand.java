package org.example.command;

import java.util.List;

import org.example.base.iomanager.IOManager;
import org.example.base.exception.*;
import org.example.exception.ServerErrorResponseExcpetion;
import org.example.network.NetworkClient;
import org.example.script.ScriptManager;

/**
 * ExecuteScriptUserCommand - класс команды для исполнения скриптов.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class ExecuteScriptUserCommand extends UserCommand {
    private final NetworkClient networkClient;
    /**
     * Конструктор класса
     * @param networkClient класс для общения с сервером
     * @param ioManager класс для работы с вводом-вывода
     */
    public ExecuteScriptUserCommand(NetworkClient networkClient, IOManager ioManager) {
        super("execute_script", "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.", ioManager);
        this.networkClient = networkClient;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args список команд типа String
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public void execute(List<String> args) throws CommandArgumentExcetpion {
        if(args.size() != 1) {
            throw new CommandArgumentExcetpion(getName() + " ожидает получить 1 аргумент");
        }

        ScriptManager scriptManager = new ScriptManager(args.get(0), networkClient);

        try {
            scriptManager.runScript();
        }
        catch (DamageScriptException | RecursionException e) {
            if(scriptManager.getRecursionDepth() == 0) {
                if (e instanceof RecursionException) {
                    ioManager.writeError("Обнаружена рекурсия. Выполнение скрипта приостановлена");
                }
                else if (e instanceof DamageScriptException) {
                    ioManager.writeError("Ошибка исполнения скрипта - скрипт поврежден");
                }
                else if (e instanceof ServerErrorResponseExcpetion) {
                    ioManager.writeError("Ошибка со стороны сервера, выполнение скрипта остановлено");
                }
                return;
            }

            throw e;
        }

        ioManager.writeLine("Скрипт успешно выполнен");
    }
}
