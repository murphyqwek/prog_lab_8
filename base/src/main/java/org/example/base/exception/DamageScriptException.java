package org.example.base.exception;


/**
 * DamageScriptException - исключение, когда файл скрипта поврежден
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class DamageScriptException extends RuntimeException {
    public DamageScriptException() {
        super();
    }

    public DamageScriptException(String message) {
        super(message);
    }
}
