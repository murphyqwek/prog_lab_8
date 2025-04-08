package org.example.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.database.UserDataBaseService;
import org.example.exception.CannotConnectToDataBaseException;
import org.example.exception.LoginIsAlreadyRegisteredException;
import org.example.exception.UserIsNotAuthorizedException;
import org.example.server.UserData;
import org.example.util.hash.HashSHA1Strategy;
import org.example.util.hash.HashStrategy;

import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * UserManager - класс менеджера авторизированных пользователей сервиса.
 *
 * @version 1.0
 */

public class UserManager {
    private final ConcurrentHashMap<InetAddress, Set<UserData>> sessions = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();
    private final Duration sessionTimeout = Duration.ofMinutes(30);
    private final Logger logger = LogManager.getLogger();

    private final UserDataBaseService userDataBase;
    private HashStrategy hashStrategy;

    private final String pepper = "iloveopd";

    public UserManager(UserDataBaseService userDataBase) {
        this.userDataBase = userDataBase;
        this.hashStrategy = new HashSHA1Strategy();
        cleaner.scheduleAtFixedRate(this::removeUnactiveUsers, 1, 60, TimeUnit.SECONDS);
    }

    public void shutdown() {
        cleaner.shutdownNow();
    }

    /**
     * Метод для получения данных об авторизированном пользователе
     * @param address
     * @param login
     * @param password
     * @exception UserIsNotAuthorizedException если пользователь не авторизирован
     * @return
     */
    public UserData getAuthorizedUser(InetAddress address, String login, String password) throws UserIsNotAuthorizedException, CannotConnectToDataBaseException {
        var userSessions = sessions.get(address);

        UserData userData = new UserData(login, password, address);

        if(userSessions == null) {
            throw new UserIsNotAuthorizedException();
        }

        for(var userDataAutorized : userSessions) {
            if(userDataAutorized.equals(userData)) {
                userDataAutorized.updateActivity();
                return userDataAutorized;
            }
        }

        String hashPassword = hashPassword(password, login, pepper);

        if(!userDataBase.validateCredentials(login, hashPassword)) {
            throw new UserIsNotAuthorizedException();
        }

        sessions.computeIfAbsent(address, k -> new HashSet<UserData>()).add(userData);

        return userData;
    }

    /**
     * Метод для проверки, авторизован ли пользователь или нет
     * @param address
     * @param login
     * @param password
     * @return
     * @throws CannotConnectToDataBaseException
     */
    public boolean isAuthorized(InetAddress address, String login, String password) throws CannotConnectToDataBaseException {
        try {
            getAuthorizedUser(address, login, password);
            return true;
        } catch (UserIsNotAuthorizedException e) {
            return false;
        }
    }

    /**
     * Метод для регистрации нового пользователя
     * @param userData
     * @return
     * @throws CannotConnectToDataBaseException
     */
    public boolean registerNewUser(UserData userData) throws CannotConnectToDataBaseException {
        try {
            String hashPassword = hashPassword(userData.getPassword(), userData.getLogin(), pepper);
            userDataBase.registerNewUser(userData.getLogin(), hashPassword);
            logger.info(String.format("Зарегистрирован новый пользователь:\nLogin: %s\nPassword: %s", userData.getLogin(), hashPassword));
        } catch(LoginIsAlreadyRegisteredException ex) {
            logger.warn(String.format("Не удалось зарегистрировать пользователя, так как логин не уникален: %s", userData.getLogin()));
            return false;
        }

        userData.updateActivity();
        sessions.computeIfAbsent(userData.getInetAddress(), k -> new HashSet<UserData>()).add(userData);

        logger.info(String.format("Зарегистрирован новый пользователь:\nLogin: %s\nPassword: %s", userData.getLogin(), userData.getPassword()));
        return true;
    }

    /**
     * Метод для хеширования паролей
     * @param password
     * @param salt
     * @param pepper
     * @return
     */
    private String hashPassword(String password, String salt, String pepper) {
        return this.hashStrategy.hash(password + salt + pepper);
    }

    /**
     * Метод для проверки неактивных пользователей и удаления их из списка авторизированных пользователей
     */
    private void removeUnactiveUsers() {
        Instant now = Instant.now();
        sessions.forEach((address, sessions) -> {
            sessions.forEach(session -> {
                if (Duration.between(session.getLastActive(), now).compareTo(sessionTimeout) > 0) {
                    logger.info("Обнаружен неактивный пользователь: " + session + ". Отключаем его");
                    sessions.remove(session);
                }
            });
        });
    }

}
