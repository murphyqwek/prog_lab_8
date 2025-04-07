package org.example.command;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final Logger logger = LogManager.getRootLogger();
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

        ScriptManager scriptManager = new ScriptManager(networkClient);

        try {
            scriptManager.runScript(args.get(0));
            ioManager.writeLine("Скрипт успешно выполнен");
        } catch (DamageScriptException e) {
            ioManager.writeError("Ошибка исполнения скрипта - скрипт поврежден");
        } catch (ServerErrorResponseExcpetion e) {
            logger.error("Ошибка со стороны сервера, выполнение скрипта остановлено");
            ioManager.writeError("Ошибка со стороны сервера, выполнение скрипта остановлено");
        } catch (RecursionException e) {
            ioManager.writeError("Обнаружена рекурсия. Выполнение скрипта приостановлена");
        }
    }
}
