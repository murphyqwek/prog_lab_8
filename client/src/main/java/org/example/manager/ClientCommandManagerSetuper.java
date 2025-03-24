package org.example.manager;

import org.example.base.iomanager.IOManager;
import org.example.command.*;
import org.example.network.NetworkClient;
import org.example.base.parser.CsvCollectionManager;
import org.example.base.file.FileManager;

/**
 * ClientCommandManagerSetuper - клас для загрузки команд в ClientCommandManager.
 *
 * @version 1.0
 */

public class ClientCommandManagerSetuper {
    /**
     * Метод для загрузки в CommandManger стандартный набор команд
     * @param ioManager класс для работы с вводом-выводом
     * @param networkClient класс для общения с сервером
     * @param commandManager класс менеджера команд
     */
    public static void SetupCommandManager(IOManager ioManager, NetworkClient networkClient, ClientCommandManager commandManager) {
        commandManager.clearCommands();

        commandManager.addCommand(new AddUserCommand(networkClient, ioManager));
        commandManager.addCommand(new UserCommandWithoutArguments("clear", "clear : очистить коллекцию", ioManager, networkClient));
        commandManager.addCommand(new FilterContainsNameUserCommand(networkClient, ioManager));
        commandManager.addCommand(new UserCommandWithoutArguments("help", "help: вывести справку по доступным командам", ioManager, networkClient));
        commandManager.addCommand(new UserCommandWithoutArguments("history", "history: вывести последние 10 команд (без их аргументов)", ioManager, networkClient));
        commandManager.addCommand(new UserCommandWithoutArguments("info", "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", ioManager, networkClient));
        commandManager.addCommand(new UserCommandWithoutArguments("print_ascending", "print_ascending : вывести элементы коллекции в порядке возрастания", ioManager, networkClient));
        commandManager.addCommand(new RemoveByIdCommand(networkClient, ioManager));
        commandManager.addCommand(new UserCommandWithoutArguments("sum_of_albums_count", "sum_of_albums_count: вывести сумму значений поля albumsCount для всех элементов коллекции", ioManager, networkClient));
        commandManager.addCommand(new ExitCommand(ioManager));
        commandManager.addCommand(new ShowUserCommand(ioManager, networkClient));
        commandManager.addCommand(new UpdateUserCommand(networkClient, ioManager));
        commandManager.addCommand(new RemoveLowerUserCommand(networkClient, ioManager));
        commandManager.addCommand(new AddIfMaxUserCommand(networkClient, ioManager));
        commandManager.addCommand(new ExecuteScriptUserCommand(networkClient, ioManager));
    }
}
