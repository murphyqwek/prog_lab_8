package org.example.script;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.exception.DamageScriptException;
import org.example.base.exception.RecursionException;
import org.example.base.file.FileReaderIterator;
import org.example.base.iomanager.FileIOManager;
import org.example.command.NetworkUserCommand;
import org.example.exception.ServerErrorResponseExcpetion;
import org.example.manager.ClientCommandManager;
import org.example.manager.ClientCommandManagerSetuper;
import org.example.network.NetworkClient;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * ScriptExtractor - класс для чтения и выгрузки скриптов.
 *
 * @version 1.0
 */

public class ScriptExtractor {
    private final Logger logger = LogManager.getRootLogger();
    private static final HashSet<String> recursionSet = new HashSet<>();
    private final NetworkClient networkClient;
    private final String filepath;
    private final ClientCommandManager clientCommandManager;

    public ScriptExtractor(NetworkClient networkClient, final String filepath) {
        this.networkClient = networkClient;
        this.filepath = new File(filepath).getAbsolutePath();
        this.clientCommandManager = new ClientCommandManager();
    }

    /**
     * Метод для проверки рекурсии
     * @return
     */
    private boolean checkRecursion() {
        return recursionSet.contains(new File(filepath).getAbsolutePath());
    }

    /**
     * Метод для получения итератора файла
     * @return
     */
    private FileReaderIterator getFileIterator() {
        try {
            return new FileReaderIterator(filepath);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Метод для считывания файла скрипта
     * @return возвращает список команд для исполнения ScriptManager
     * @throws RecursionException если обнаружилась рекурсия
     */
    public List<RecursionExecutable> extractCommandsFromScript() throws RecursionException, DamageScriptException {
        logger.info("Чтения скрипта " + filepath);
        LinkedList<RecursionExecutable> recursionCommands = new LinkedList<>();

        if(checkRecursion()) {
            logger.warn("Обнаружена рекурсия");
            throw new RecursionException();
        }

        recursionSet.add(filepath);

        FileReaderIterator fileReaderIterator = getFileIterator();
        if(fileReaderIterator == null) {
            recursionSet.remove(filepath);
            logger.error("Не удалось получить итератор файла по пути: " + filepath);
            throw new DamageScriptException();
        }

        ClientCommandManagerSetuper.SetupCommandManager(new FileIOManager(fileReaderIterator), networkClient, clientCommandManager);


        try {
            while(fileReaderIterator.hasNext()) {
                String line = fileReaderIterator.next();
                line = line.replace("\n", "").replace("\r", "");

                if(line.trim().isEmpty()) {
                    continue;
                }

                if(checkForExecuteScript(line, recursionCommands)) {
                    continue;
                }

                List<String> argument = clientCommandManager.parsingInputCommand(line);
                String commandName = argument.get(0);
                argument.remove(0);

                var recursionExecutable = getExecutableForScriptManager(commandName, argument);

                recursionCommands.add(recursionExecutable);
            }
        }
        finally {
            recursionSet.remove(filepath);
            fileReaderIterator.close();
        }

        logger.info("Скрипт прочитан");
        return recursionCommands;
    }

    /**
     * Метод для проверки команды execute_script и её исполнения
     * @param line
     * @param recursionCommands список команд для ScriptManager
     * @return true, если в строке содержалась правильная команда execute_script, false в противном случае
     * @throws RecursionException
     * @throws DamageScriptException
     */
    private boolean checkForExecuteScript(String line, List<RecursionExecutable> recursionCommands) throws RecursionException, DamageScriptException {
        var splittedLine = line.trim().split(" ");

        if(splittedLine.length < 2) {
            return false;
        }

        if(!splittedLine[0].equals("execute_script")) {
            return false;
        }

        String scriptPath = String.join("", Arrays.stream(splittedLine).toList().subList(1, splittedLine.length));

        ScriptExtractor extractor = new ScriptExtractor(networkClient, scriptPath);

        recursionCommands.addAll(extractor.extractCommandsFromScript());

        return true;
    }

    private RecursionExecutable getExecutableForScriptManager(String name, List<String> args) throws DamageScriptException {
        var command = clientCommandManager.getUserCommandByName(name);

        if(command == null) {
            throw new DamageScriptException("Нет команды: " + name);
        }

        if(command instanceof NetworkUserCommand networkUserCommand) {
            try {
                var response = networkUserCommand.getClientCommandRequest(args);
                if(response == null) {
                    throw new DamageScriptException("Неверные аргументы к команде " + name);
                }

                return new RecursionCommandWithRequest(networkUserCommand, response);
            } catch (CommandArgumentExcetpion e) {
                logger.error("Неверные аргументы к команде" + name);
                throw new DamageScriptException("Неверные аргументы к команде " + name);
            } catch (Exception e) {
                logger.error("Неверные аргументы к команде" + name);
                throw new DamageScriptException("Неверные аргументы к команде " + name);
            }
        }

        return new RecursionCommand(command, Collections.emptyList());
    }
}
