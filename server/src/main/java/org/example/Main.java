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
        if(args.length == 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) { }
        }


        logger.info("Инициализация сервера. Порт: " + port);

        try {
            Run run = new Run(port);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    run.shutdown();
                    logger.info("Выключение сервера...");
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (RuntimeException e) {
                    System.err.println("Ошибка работы сервера: " + e.getMessage());
                    logger.error("Не удалось сохранить коллекцию");
                }
                logger.info("Завершение работы сервера");
            }));

            run.run();
        } catch (IOException e) {
            System.err.println("Ошибка работы сервера: " + e.getMessage());
            logger.error("Ошибка работы сервера: " + e.getMessage());
        } catch (Exception ex) {
            logger.error("Неожиданная ошибка");
            logger.error(ex.getMessage());
            logger.error("Аварийное завершение");
            System.exit(-1);
        }
    }
}