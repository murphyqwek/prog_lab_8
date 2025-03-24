package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.file.FileManager;
import org.example.base.iomanager.IOManager;
import org.example.base.iomanager.StandartIOManager;
import org.example.exception.ServerErrorResponseExcpetion;
import org.example.manager.ClientCommandManager;
import org.example.manager.ClientCommandManagerSetuper;
import org.example.network.NetworkClient;

import java.util.NoSuchElementException;

/**
 * Run - класс для работы приложения.
 *
 * @version 1.0
 */

public class Run {
    private final Logger logger = LogManager.getLogger();
    private ClientCommandManager commandManager;
    private IOManager ioManager;
    private NetworkClient networkClient;

    /**
     * Конструктор класса
     * @param networkClient класс для общения с сервером
     */
    public Run(NetworkClient networkClient) {
        this.commandManager = new ClientCommandManager();
        this.ioManager = new StandartIOManager();
        this.networkClient = networkClient;
        setup();
    }

    /**
     * Метод для подготовки программы
     */
    private void setup() {
        ClientCommandManagerSetuper.SetupCommandManager(this.ioManager, this.networkClient, this.commandManager);
    }

    /**
     * Запускает программу
     */
    public void run() {
        while(true) {
            cycle();
        }
    }

    /**
     * Метод цикла программы
     */
    private void cycle() {
        ioManager.writeLine("Введите команду: ");

        String command = "";
        try {
            command = ioManager.readLine();
            commandManager.execute(command);
        }
        catch (NoSuchElementException e) {
            logger.info("Завершения работы");
            System.exit(0);
        }
        catch (ServerErrorResponseExcpetion e) {
            if(e.isConnectionError()) {
                logger.error("Ошибка общения с сервером");
                logger.error(e.getMessage());
                logger.info("Завершения работы");
                System.exit(0);
            }
            else {
                ioManager.writeError("Ответ сервера пришел поврежденным или с пометкой ошибка");
                ioManager.writeLine(e.getMessage());
            }
        }

    }
}
