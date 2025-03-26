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

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

/**
 * ScriptManager - класс для запуска скриптов и мониторинга рекрусии.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class ScriptManager {
    private static final HashSet<String> recursionSet;
    private final String filepath;
    private final NetworkClient networkClient;
    private final ClientCommandManager clientCommandManager;
    private final Logger logger = LogManager.getRootLogger();

    static {
        recursionSet = new HashSet<>();
    }

    /**
     * Конструктор класса
     * @param filepath путь к скрипту
     * @param networkClient класс для общения с сервером
     */
    public ScriptManager(String filepath, NetworkClient networkClient) {
        this.filepath = new File(filepath).getAbsolutePath();
        this.networkClient = networkClient;
        this.clientCommandManager = new ClientCommandManager();
    }

    /**
     * Метод для запуска исполнения скриптов
     * @throws RecursionException если возникла рекурсия
     * @throws DamageScriptException если файл скрипта поврежден и не может быть корректно выполенен
     */
    public void runScript() throws RecursionException, DamageScriptException {
        logger.info("Запуск скрипта: " + filepath);
        if(recursionSet.contains(new File(filepath).getAbsolutePath())) {
            logger.warn("Обнаружена рекурсия");
            throw new RecursionException();
        }

        recursionSet.add(filepath);

        FileReaderIterator fileReaderIterator;
        try {
            fileReaderIterator = new FileReaderIterator(filepath);
        } catch (IOException e) {
            recursionSet.remove(filepath);
            throw new DamageScriptException();
        }

        ClientCommandManagerSetuper.SetupCommandManager(new FileIOManager(fileReaderIterator), networkClient, clientCommandManager);


        while(fileReaderIterator.hasNext()) {
            String line = fileReaderIterator.next();
            line = line.replace("\n", "").replace("\r", "");

            try {
                clientCommandManager.execute(line);
            }
            catch (ServerErrorResponseExcpetion ex) {
                if(ex.isConnectionError()) {
                    throw ex;
                }
            }
            catch (RecursionException e) {
                throw e;
            }
            catch(Exception e) {
                throw new DamageScriptException();
            }
            finally {
                recursionSet.remove(filepath);
                fileReaderIterator.close();
            }
        }


    }

    /**
     * Метод для получения текущей глубины рекурсии
     */
    public int getRecursionDepth() {
        return recursionSet.size();
    }

}
