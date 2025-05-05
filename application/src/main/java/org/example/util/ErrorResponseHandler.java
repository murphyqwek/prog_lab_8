package org.example.util;

import org.example.base.response.ServerErrorType;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseError;
import org.example.command.ExecuteScriptStatus;
import org.example.exception.ExecuteAppCommandExcpetion;
import org.example.localization.Localization;

/**
 * ErrorResponseHandler - описание класса.
 *
 * @version 1.0
 */

public class ErrorResponseHandler {
    public static void checkForScriptError(ExecuteScriptStatus status) {
        var text = switch (status) {
            case RECURSION -> Localization.get("recursion");
            case SUCCESS -> null;
            case DAMAGED_SCRIPT -> Localization.get("damaged_script");
            case SERVER_ERROR -> Localization.get("server_error");
        };

        if(text != null) {
            throw new ExecuteAppCommandExcpetion(text);
        }
    }

    public static void checkForErrorResponse(ServerResponse response) {
        if(response instanceof ServerResponseError) {
            handleErrorReponse((ServerResponseError) response);
        }
    }

    public static void handleErrorReponse(ServerErrorType errorType) throws ExecuteAppCommandExcpetion {
        var text = switch (errorType) {
            case BD_FALL -> Localization.get("bd_fall");
            case TIMEOUT -> Localization.get("timeout");
            case CORRUPTED -> Localization.get("corrupted");
            case INTERRUPTED -> Localization.get("interrupted");
            case NO_RESPONSE -> Localization.get("no_response");
            case UNAUTHORIZED -> Localization.get("unauthorized");
            case CANNOT_DELETE -> Localization.get("cannot_delete");
            case DID_NOT_UPDATE -> Localization.get("did_not_update");
            case CANNOT_ADD_TO_BD -> Localization.get("cannot_add_to_bd");
            case UNEXPECTED_RESPONSE -> Localization.get("unexpected_response");
            case DO_NOT_OWN_BY_USER -> Localization.get("do_not_own_by_user");
            case DID_NOT_FIND_ELEMENT -> Localization.get("did_not_find_element");
            case EXISTING_LOGIN -> Localization.get("existing_login");
            default -> Localization.get("unknown_error");
        };

        throw new ExecuteAppCommandExcpetion(text);
    }

    public static void handleErrorReponse(ServerResponseError responseError) throws ExecuteAppCommandExcpetion {
        handleErrorReponse(responseError.getServerErrorType());
    }
}
