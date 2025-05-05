package org.example.controller;

import org.example.UserLoginPasswordContainer;
import org.example.base.model.MusicBand;
import org.example.base.model.MusicBandWithOwner;
import org.example.base.response.ServerErrorType;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseMusicBandOwners;
import org.example.base.response.ServerResponseUpdate;
import org.example.command.ShowOwnersAppCommand;
import org.example.command.SubscribeAppCommand;
import org.example.command.UnsubscribeAppCommand;
import org.example.model.LocalStorage;
import org.example.model.LocalStorageVisualize;
import org.example.network.NetworkClient;
import org.example.util.ErrorResponseHandler;

import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * VisualiseController - описание класса.
 *
 * @version 1.0
 */

public class VisualiseController {
    private LocalStorageVisualize localStorage;
    private NetworkClient networkClient;

    public VisualiseController(String ip, int port, UserLoginPasswordContainer credentials) {
        this.localStorage = new LocalStorageVisualize();
        this.networkClient = new NetworkClient(ip, port, credentials);

        subscribe();

        updateLocalStorage();
    }

    public void subscribe() {
        SubscribeAppCommand appCommand = new SubscribeAppCommand(networkClient);

        appCommand.appExecute();
    }

    public void unsubscribe() {
        UnsubscribeAppCommand appCommand = new UnsubscribeAppCommand(networkClient);

        try {
            appCommand.appExecute();
        } catch (Exception ex) {
            return;
        }
    }

    public List<MusicBandWithOwner> getMusicBands() {
        return localStorage.getStorage();
    }

    public void updateLocalStorage() {
        ShowOwnersAppCommand appCommand = new ShowOwnersAppCommand(networkClient);

        var response = appCommand.appExecute();

        ErrorResponseHandler.checkForErrorResponse(response);

        if(!(response instanceof ServerResponseMusicBandOwners)) {
            ErrorResponseHandler.handleErrorReponse(ServerErrorType.UNEXPECTED_RESPONSE);
        }

        var res = (ServerResponseMusicBandOwners) response;

        localStorage.setMusicBands(res.getMusicBandWithOwners());
    }

    public void checkForUpdate() {
        var response = networkClient.getServerResponse();

        ErrorResponseHandler.checkForErrorResponse(response);

        if(!(response instanceof ServerResponseUpdate)) {
            ErrorResponseHandler.handleErrorReponse(ServerErrorType.UNEXPECTED_RESPONSE);
        }

        updateLocalStorage();
    }
}
