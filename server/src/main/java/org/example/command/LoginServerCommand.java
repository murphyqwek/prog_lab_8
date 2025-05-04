package org.example.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.CommandArgumentExcetpion;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.exception.CannotConnectToDataBaseException;
import org.example.exception.UserIsNotAuthorizedException;
import org.example.manager.UserManager;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.List;

/**
 * LoginUserCommand - описание класса.
 *
 * @version 1.0
 */

public class LoginServerCommand extends UserCommand {
    private final Logger logger = LogManager.getLogger();
    private final UserManager userManager;
    private final InetAddress userIP;
    /**
     * Конструктор класса
     *
     * @param userManager
     */
    public LoginServerCommand(UserManager userManager, InetAddress address) {
        super("login", "login - команда авторизации пользователя");

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
        logger.info("Пользователь " + login + " хочет авторизоваться");

        if(args.size() != 2) {
            throw new CommandArgumentExcetpion("Пользователь не отправил пароль или логин");
        }

        if(!(args.get(0) instanceof String) || !(args.get(1) instanceof String)) {
            throw new CommandArgumentExcetpion("Сервер получил неправильный запрос на авторизацию");
        }

        String loginAut = (String) args.get(0);
        String passwordAut = (String) args.get(1);

        if(loginAut == null || passwordAut == null) {
            throw new CommandArgumentExcetpion("Логин и пароль не могут быть null");
        }

        ServerResponse response;

        try {
            userManager.getAuthorizedUser(userIP, loginAut, passwordAut);
            response = new ServerResponse(ServerResponseType.SUCCESS, "Вы успешно авторизовались!");
            logger.info("Пользователь " + login + " успешно авторизовался");
        } catch (CannotConnectToDataBaseException e) {
            response = new ServerResponse(ServerResponseType.ERROR, "Ошибка соединения с базой данных");
        } catch (UserIsNotAuthorizedException e) {
            response = new ServerResponse(ServerResponseType.UNAUTHORIZED, "Ваш логин или пароль неверен");
        }

        return response;
    }
}
