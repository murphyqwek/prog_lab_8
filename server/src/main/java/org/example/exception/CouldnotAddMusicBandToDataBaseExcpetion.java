package org.example.exception;

/**
 * CouldnotAddMusicBandToDataBaseExcpetion - исключение, когда не удалось добавить элемент в коллекцию в дата базе
 */
public class CouldnotAddMusicBandToDataBaseExcpetion extends RuntimeException {
    public CouldnotAddMusicBandToDataBaseExcpetion() {
        super();
    }
    public CouldnotAddMusicBandToDataBaseExcpetion(String message) {
        super(message);
    }
}
