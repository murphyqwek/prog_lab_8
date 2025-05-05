package org.example.controller;

import org.example.base.model.Coordinates;
import org.example.base.model.Label;
import org.example.base.model.MusicBand;
import org.example.base.model.MusicGenre;
import org.example.base.response.ServerErrorType;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseBollean;
import org.example.base.response.ServerResponseError;
import org.example.command.AddIfMaxUserCommand;
import org.example.command.AddUserCommand;
import org.example.exception.InvalidMusicBandExcpetion;
import org.example.localization.Localization;
import org.example.network.NetworkClient;
import org.example.util.ErrorResponseHandler;

import javax.swing.*;

/**
 * AddController - описание класса.
 *
 * @version 1.0
 */

public class AddController {
    private NetworkClient networkClient;
    private boolean isCanceled;
    private boolean isSaved;

    public AddController(NetworkClient networkClient) {
        this.networkClient = networkClient;
        isCanceled = true;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public String save(String name, Integer x, long y, Long numberofParticipants, long albumsCount, String genre, double sales, boolean addIfMax) {
        MusicBand newMusicBand = new MusicBand(1, name, new Coordinates(x, y), numberofParticipants, albumsCount, MusicGenre.valueOf(genre), new Label(sales));

        if(addIfMax) {
            AddIfMaxUserCommand addIfMaxUserCommand = new AddIfMaxUserCommand(networkClient);
            var response = addIfMaxUserCommand.appExecute(newMusicBand);
            ErrorResponseHandler.checkForErrorResponse(response);

            if(!(response instanceof ServerResponseBollean)) {
                ErrorResponseHandler.handleErrorReponse(ServerErrorType.UNEXPECTED_RESPONSE);
            }

            var responseWithFlag = (ServerResponseBollean) response;

            if(responseWithFlag.isFlag()) {
                isSaved = true;
                return Localization.get("add_if_max_added");
            }
            else {
                return Localization.get("not_add_if_max_added");
            }
        }
        else {
            AddUserCommand addUserCommand = new AddUserCommand(networkClient);
            var response = addUserCommand.appExecute(newMusicBand);
            ErrorResponseHandler.checkForErrorResponse(response);
            return Localization.get("add_user_added");
        }
    }

    public void close(JDialog dialog, boolean isCanceled) {
        this.isCanceled = isCanceled;
        dialog.dispose();
    }
}
