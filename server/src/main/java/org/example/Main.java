package org.example;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {
    private static final Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {
        int port = 2720;
        String collectionFile = "./collection.csv";

        if(args.length == 1) {
            collectionFile = args[0];
        }

        if(args.length == 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) { }
        }

        try {
            Path logDir = Paths.get("logs");
            Files.createDirectories(logDir);
        } catch (Exception e) {
            System.err.println("Ошибка при создании каталога логов: " + e.getMessage());
            return;
        }

        logger.info("Инициализация сервера. Порт: " + port + " путь к файлу коллекции: " + collectionFile);

        try {
            Run run = new Run(port, collectionFile);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    run.saveCollection();
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (IOException e) {
                    System.err.println("Ошибка работы сервера: " + e.getMessage());
                    logger.error("Не удалось сохранить коллекцию");
                }
                logger.info("Завершение работы сервера");
            }));

            run.run();
        } catch (IOException e) {
            System.err.println("Ошибка работы сервера: " + e.getMessage());
            logger.error("Ошибка работы сервера: " + e.getMessage());
        }
    }
}