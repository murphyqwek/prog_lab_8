package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.file.FileManager;
import org.example.base.iomanager.IOManager;
import org.example.base.iomanager.StandartIOManager;
import org.example.command.RegisterUserCommand;
import org.example.exception.ServerErrorResponseExcpetion;
import org.example.manager.ClientCommandManager;
import org.example.manager.ClientCommandManagerSetuper;
import org.example.network.NetworkClient;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Run - класс для работы приложения.
 *
 * @version 1.0
 */


public class Run {
    private final Logger logger = LogManager.getRootLogger();
    private ClientCommandManager commandManager;
    private IOManager ioManager;
    private NetworkClient networkClient;
    private final UserLoginPasswordContainer userLoginPasswordContainer = new UserLoginPasswordContainer();

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
        getCredentialsAndRegiser();

        while(true) {
            cycle();
        }
    }

    private void getCredentialsAndRegiser() {
        while(true) {
            var userChoice = askUserMode();
            askCredentials();

            if (userChoice == UserInit.REGISTER) {
                try {
                    new RegisterUserCommand(ioManager, networkClient, userLoginPasswordContainer).execute(Collections.emptyList());
                } catch (CommandArgumentExcetpion e) {
                    continue;
                } catch (ServerErrorResponseExcpetion e) {
                    if(e.isConnectionError()) {
                        System.exit(0);
                    }

                    ioManager.writeError(e.getMessage());
                    ioManager.writeLine("Попробуйте другой логин");

                    continue;
                }
            }

            break;
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
            ioManager.writeLine("Завершение работы");
            logger.info("Завершения работы");
            System.exit(0);
        }
        catch (ServerErrorResponseExcpetion e) {
            if(e.isConnectionError()) {
                logger.error("Ошибка общения с сервером");
                logger.error(e.getMessage());
                logger.info("Завершение работы");
                ioManager.writeError("Ошибка сервера");
                ioManager.writeError(e.getMessage());
                ioManager.writeLine("Завершение работы");
                System.exit(1);
            }
            else {
                logger.warn("Ответ сервера пришел поврежденным или с пометкой ошибка");
                logger.warn(e.getMessage());
                ioManager.writeError("Ответ сервера пришел поврежденным или с пометкой ошибка");
                ioManager.writeLine(e.getMessage());
            }
        }

    }

    private static UserInit askUserMode() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите режим: [1] Войти  [2] Зарегистрироваться");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1") || choice.equalsIgnoreCase("войти")) {
                return UserInit.LOGIN;
            } else if (choice.equals("2") || choice.equalsIgnoreCase("зарегистрироваться")) {
                return UserInit.REGISTER;
            } else {
                System.out.println("Неверный выбор. Пожалуйста, введите 1 или 2.");
            }
        }
    }

    private void askCredentials() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите логин: ");
        String login = scanner.nextLine().trim();

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine().trim();

        userLoginPasswordContainer.setLogin(login);
        userLoginPasswordContainer.setPassword(password);
    }
}
