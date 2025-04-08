package org.example.exception;

/**
 * CannotUpdateMusicBandException - исключение, когда не удалось обновить элемент в коллекции в базе данных
 */
public class CannotUpdateMusicBandException extends RuntimeException {
    public CannotUpdateMusicBandException() { super();}
    public CannotUpdateMusicBandException(String message) {
        super(message);
    }
}
