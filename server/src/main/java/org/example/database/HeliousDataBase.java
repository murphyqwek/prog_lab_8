package org.example.database;

/**
 * HeliousDataBase - класс для подключения к базе данных на гелиосе.
 *
 * @version 1.0
 */

public class HeliousDataBase extends DatabaseConnection {
    public HeliousDataBase(String login, String password) {
        super("jdbc:postgresql://localhost:5432/studs", login, password);
    }
}
