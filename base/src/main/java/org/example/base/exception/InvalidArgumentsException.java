package org.example.base.exception;

/**
 * InvalidArgumentsException - исключение, когда аргумент не соответствует ожидаемому типу или не проходит ограничения
 *
 * @author Starikov Arseny
 * @version 1.0
 */


public class InvalidArgumentsException extends RuntimeException {
  public InvalidArgumentsException(String message) {
    super(message);
  }
}
