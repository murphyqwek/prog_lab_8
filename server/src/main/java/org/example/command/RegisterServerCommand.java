package org.example.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.exception.CannotConnectToDataBaseException;
import org.example.manager.UserManager;
import org.example.server.UserData;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.List;

/**
 * RegisterServerCommand - класс команды для регистрации.
 *
 * @version 1.0
 */

public class RegisterServerCommand extends UserCommand {
    private final UserManager userManager;
    private final Logger logger = LogManager.getRootLogger();
    private final InetAddress userIP;
    /**
     * Конструктор класса
     *
     * @param userManager
     */
    public RegisterServerCommand(UserManager userManager, InetAddress address) {
        super("register", "register - команда регистрации пользователя");

        this.userManager = userManager;
        this.userIP = address;
    }

    /**
     * Метод для выполнения команд
     *
     * @param args  список команд типа String
     * @param login
     * @throws CommandArgumentExcetpion если количество требуемых аргументов не соответствует количеству переданных аргументов, а также если команда не принимает никаких аргументов, но список аргументов не пуст
     */
    @Override
    public ServerResponse execute(List<Serializable> args, String login) throws CommandArgumentExcetpion {
        logger.info("Пользователь хочет зарегистрироваться");

        if(args.size() != 2) {
            throw new CommandArgumentExcetpion("Пользователь не отправил пароль или логин");
        }

        if(!(args.get(0) instanceof String) || !(args.get(1) instanceof String)) {
            throw new CommandArgumentExcetpion("Сервер получил неправильный запрос на регистрацию");
        }

        String loginReg = (String) args.get(0);
        String passwordReg = (String) args.get(1);

        if(loginReg == null || passwordReg == null) {
            throw new CommandArgumentExcetpion("Логин и пароль не могут быть null");
        }

        ServerResponse response;

        try {

            if(userManager.registerNewUser(new UserData(loginReg, passwordReg, userIP))) {
                response = new ServerResponse(ServerResponseType.SUCCESS, "Вы успешно зарегистрированы");
            }
            else {
                response = new ServerResponse(ServerResponseType.FAILURE, "Ваш логин не уникален");
            }
        } catch (CannotConnectToDataBaseException e) {
            response = new ServerResponse(ServerResponseType.ERROR, "Ошибка соединения с базой данных");
        }

        return response;
    }
}
