package org.example.base.response;

/**
 * ServerResponseType - перечисления типов ответа.
 *
 * @version 1.0
 */

public enum ServerResponseType {
    SUCCESS,
    ERROR,
    CORRUPTED, // Когда ответ от клиента пришел битым
}
