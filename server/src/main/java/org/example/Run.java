package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.DeserializationException;
import org.example.base.model.MusicBand;
import org.example.base.parser.CsvCollectionManager;
import org.example.database.CollectionDataBaseService;
import org.example.database.DatabaseConnector;
import org.example.database.HeliousDataBase;
import org.example.database.UserDataBaseService;
import org.example.manager.CollectionManager;
import org.example.manager.ServerCommandManager;
import org.example.manager.ServerCommandManagerSetuper;
import org.example.manager.UserManager;
import org.example.server.Server;
import org.example.base.iomanager.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Run - класс для запуска приложения.
 *
 * @version 1.0
 */

public class Run {
    private final Logger logger = LogManager.getRootLogger();
    private Server server;
    private final CollectionManager collectionManager;
    private final ServerCommandManager commandManager;

    private UserDataBaseService userDataBaseService;
    private CollectionDataBaseService collectionDataBaseService;

    private final int port;

    public Run(int port) throws IOException {
        this.collectionManager = new CollectionManager();
        this.commandManager = new ServerCommandManager();
        this.port = port;
        setup();
    }

    private void setupDataBased() {
        try {
            var connection = DatabaseConnector.getConnection();
            userDataBaseService = new UserDataBaseService(connection);
            collectionDataBaseService = new CollectionDataBaseService(connection);

            userDataBaseService.init();
            collectionDataBaseService.init();
        } catch (Exception e) {
            logger.error("Не удалось подключиться к базе данных или её проинициализировать:\n" + e.getMessage());
            logger.error("Аварийное отключение");
            System.exit(1);
        }
    }

    private void uploadCollection() {
        try {
            for(var element : collectionDataBaseService.loadInMemory()) {
                collectionManager.addNewMusicBand(element.getMusicBand(), element.getOwner());
            }
        } catch (SQLException e) {
            logger.error("Не удалось загрузить коллекцию из базы данных:\n" + e.getMessage());
            logger.error("Аварийное отключение");
            System.exit(1);
        }
    }

    private void setup() throws IOException {
        logger.info("Инициализация базы данных...");
        setupDataBased();
        logger.info("Базы данны проинициализированы");
        logger.info("Начало загрузки коллекции...");
        uploadCollection();
        logger.info("Коллекция загружена");
        ServerCommandManagerSetuper.setupCommandManager(collectionManager, commandManager, collectionDataBaseService);
        logger.info("Инициализация сервера...");

        UserManager userManager = new UserManager(userDataBaseService);
        server = new Server(commandManager, port, userManager);
    }

    public void run() throws IOException {
        server.cycle();
    }

    public void shutdown() {
        server.shutdown();
    }
}
