package org.example.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exception.CannotConnectToDataBaseException;
import org.example.exception.LoginIsAlreadyRegisteredException;
import org.example.server.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserDataBaseService - класс для работы с базой данных пользователей.
 *
 * @version 1.0
 */

public class UserDataBaseService {
    private final Connection connection;
    private final Logger logger = LogManager.getRootLogger();

    public UserDataBaseService(Connection connection) {
        this.connection = connection;
    }

    public void init() {
        logger.info("Инициализация базы данных пользователей");
        String sql = "CREATE TABLE IF NOT EXISTS Users(id SERIAL PRIMARY KEY, login TEXT NOT NULL UNIQUE, password TEXT NOT NULL);";
        try {
            var statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException ex) {
            logger.error("Не удалось выполнить запрос к базе данных к таблице Users");
            throw new CannotConnectToDataBaseException();
        }
    }

    /**
     * Метод для регистрации нового пользователя. Хеширование пароля лежит на вызывающем коде
     * @param login
     * @param password захешированный пароль
     * @throws LoginIsAlreadyRegisteredException
     * @throws CannotConnectToDataBaseException
     */
    public void registerNewUser(String login, String password) throws LoginIsAlreadyRegisteredException, CannotConnectToDataBaseException {
        String sql = "INSERT INTO Users (login, password) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getSQLState().equals("23505")) {
                logger.warn("Не удалось зарегистрировать нового пользователя, так как его логин уже есть в базе данных");
                throw new LoginIsAlreadyRegisteredException();
            }
            else {
                logger.error("Не удалось создать запрос к базе данных к таблице Users");
                throw new CannotConnectToDataBaseException();
            }
        }
    }

    public boolean validateCredentials(String login, String password) throws CannotConnectToDataBaseException {
        String sql = "SELECT 1 FROM users WHERE login = ? AND password = ? LIMIT 1;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet result = preparedStatement.executeQuery();
            return result.next();

        } catch (SQLException ex) {
            logger.error("Не удалось создать запрос к базе данных к таблице Users");
            throw new CannotConnectToDataBaseException();
        }
    }
}
