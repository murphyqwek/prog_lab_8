package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection - класс для подключения к базе данных.
 *
 * @version 1.0
 */

public class DatabaseConnection {
    private final String dbLink;
    private final String login;
    private final String password;

    public DatabaseConnection(final String dbLink, final String login, final String password) {
        this.dbLink = dbLink;
        this.login = login;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(dbLink, login, password);
        return connection;
    }
}
