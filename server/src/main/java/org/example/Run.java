package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.DeserializationException;
import org.example.base.model.MusicBand;
import org.example.base.parser.CsvCollectionManager;
import org.example.manager.CollectionManager;
import org.example.manager.ServerCommandManager;
import org.example.manager.ServerCommandManagerSetuper;
import org.example.server.Server;
import org.example.base.iomanager.*;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Run - класс для запуска приложения.
 *
 * @version 1.0
 */

public class Run {
    private final Logger logger = LogManager.getRootLogger();
    private final Server server;
    private final IOManager ioManager;
    private final CollectionManager collectionManager;
    private final ServerCommandManager commandManager;
    private final CsvCollectionManager csvCollectionManager;
    private final String filepath;

    public Run(int port, String filepath) throws IOException {
        this.collectionManager = new CollectionManager();
        this.commandManager = new ServerCommandManager();
        this.filepath = filepath;
        this.ioManager = new StandartIOManager();
        this.csvCollectionManager = new CsvCollectionManager(filepath);
        this.server = new Server(commandManager, port, t-> saveCollection());
        setup();
    }

    private void uploadCollection() {
        File f = new File(this.filepath);

        try {
            if (!f.isFile()) {
                f.createNewFile();
                return;
            }
        }
        catch (IOException e) {
            logger.error("Не удалось создать файл коллекции, проверьте, что вы имеете права на создания и модифицирование файлов");
            System.exit(0);
        }

        try {
            Collection<MusicBand> collection = csvCollectionManager.uploadCollection();

            for(var mb : collection) {
                this.collectionManager.addNewMusicBand(mb);
            }
        } catch (IOException e) {
            logger.error("Не удалось загрузить файл, так как он не существует или уже открыт");
            System.exit(0);
        } catch (DeserializationException e) {
            logger.error("Не удалось загрузить коллекцию, так как файл поврежден");
            System.exit(0);
        }
    }

    private void setup() {
        uploadCollection();
        logger.info("Коллекция загружена");
        ServerCommandManagerSetuper.setupCommandManager(collectionManager, commandManager);
    }

    public void run() throws IOException {
        server.cycle();
    }

    public void saveCollection() {
        try {
            csvCollectionManager.saveCollection(collectionManager.getCollection());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
