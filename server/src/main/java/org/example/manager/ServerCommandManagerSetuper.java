package org.example.manager;

import org.example.command.*;
import org.example.database.CollectionDataBaseService;

/**
 * ServerCommandManagerSetuper - класс для загрузки команд в командный менеджер сервера
 *
 * @version 1.0
 */

public class ServerCommandManagerSetuper {
    /**
     * Метод для загрузки в CommandManger стандартный набор команд
     * @param collectionManager класс для управления коллекцией
     * @param commandManager класс менеджера команд
     */
    public static void setupCommandManager(CollectionManager collectionManager, ServerCommandManager commandManager, CollectionDataBaseService collectionDataBaseService) {
        commandManager.addCommand(new AddUserCommand(collectionManager, collectionDataBaseService));
        commandManager.addCommand(new ClearUserCommand(collectionManager, collectionDataBaseService));
        commandManager.addCommand(new FilterContainsNameUserCommand(collectionManager));
        commandManager.addCommand(new HelpUserCommand(commandManager));
        commandManager.addCommand(new HistoryUserCommand(commandManager.getCommandHistoryManager()));
        commandManager.addCommand(new InfoUserCommand(collectionManager));
        commandManager.addCommand(new PrintAscendingUserCommand(collectionManager));
        commandManager.addCommand(new RemoveByIdCommand(collectionManager, collectionDataBaseService));
        commandManager.addCommand(new SumOfAlbumsCountUserCommand(collectionManager));
        commandManager.addCommand(new ShowUserCommand(collectionManager));
        commandManager.addCommand(new UpdateUserCommand(collectionManager, collectionDataBaseService));
        commandManager.addCommand(new RemoveLowerUserCommand(collectionManager, collectionDataBaseService));
        commandManager.addCommand(new AddIfMaxUserCommand(collectionManager, collectionDataBaseService));
        commandManager.addCommand(new ShowWithOwnersServerCommand(collectionManager));
    }
}
