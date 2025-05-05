package org.example.base.response;

/**
 * ServerErrorType - описание класса.
 *
 * @version 1.0
 */

public enum ServerErrorType {
    BD_FALL,
    CANNOT_ADD_TO_BD,
    CANNOT_DELETE,
    DID_NOT_FIND_ELEMENT,
    UNAUTHORIZED,
    TIMEOUT,
    CORRUPTED,
    DO_NOT_OWN_BY_USER,
    DID_NOT_UPDATE,
    NO_RESPONSE,
    UNEXPECTED_RESPONSE,
    INTERRUPTED,
    EXISTING_LOGIN,
}
