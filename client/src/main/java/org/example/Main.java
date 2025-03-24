package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exception.CouldnotConnectException;
import org.example.network.NetworkClient;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        NetworkClient networkClient;
        String ip = "localhost";

        if(args.length != 0) {
            ip = args[0];
        }

        logger.info("Запуск приложения");

        try {
            networkClient = new NetworkClient(ip, 2720);
        } catch (CouldnotConnectException ex) {
            logger.error(ex.getMessage());
            return;
        }

        try {
            Path logDir = Paths.get("logs");
            Files.createDirectories(logDir);
        } catch (Exception e) {
            System.err.println("Ошибка при создании каталога логов: " + e.getMessage());
            return;
        }

        Run run = new Run(networkClient);
        run.run();
    }
}