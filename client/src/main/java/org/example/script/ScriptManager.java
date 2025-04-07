package org.example.script;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.DamageScriptException;
import org.example.base.exception.RecursionException;
import org.example.base.file.FileManager;
import org.example.base.file.FileReaderIterator;
import org.example.exception.ServerErrorResponseExcpetion;
import org.example.manager.ClientCommandManager;
import org.example.manager.ClientCommandManagerSetuper;
import org.example.base.iomanager.FileIOManager;
import org.example.base.parser.CsvCollectionManager;
import org.example.network.NetworkClient;

import java.util.List;

/**
 * ScriptManager - класс для запуска скриптов и мониторинга рекрусии.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class ScriptManager {
    private final NetworkClient networkClient;
    private final ClientCommandManager clientCommandManager;
    private final Logger logger = LogManager.getRootLogger();


    /**
     * Конструктор класса
     * @param networkClient класс для общения с сервером
     */
    public ScriptManager(NetworkClient networkClient) {
        this.networkClient = networkClient;
        this.clientCommandManager = new ClientCommandManager();
    }

    /**
     * Метод для запуска исполнения скриптов
     * @param filepath путь к скрипту
     * @throws RecursionException если возникла рекурсия
     * @throws DamageScriptException если файл скрипта поврежден и не может быть корректно выполенен
     */
    public void runScript(String filepath) throws RecursionException, DamageScriptException {
        logger.info("Запуск менедежра скриптов");
        logger.info("Загрузка всех команд");

        List<RecursionExecutable> toExecute;

        var scriptExtractor = new ScriptExtractor(networkClient, filepath);
        toExecute = scriptExtractor.extractCommandsFromScript();

        logger.info("Скрипт прочитан, ошибок нет");

        for(var executable : toExecute) {
            executable.execute();
        }

        logger.info("Скрипт выполнен успешно");
    }

}
