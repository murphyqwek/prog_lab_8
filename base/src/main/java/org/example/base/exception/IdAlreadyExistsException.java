package org.example.base.exception;

/**
 * IdAlreadyExistsException - исключение, когда при добавлении нового элемента в коллекцию, там уже есть эллемент с таким же значением id
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class IdAlreadyExistsException extends RuntimeException {
    public IdAlreadyExistsException(int id) {
        super("MusicBand c id " + id + " уже существует");
    }
}
