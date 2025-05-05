package org.example.controller;

import org.example.base.model.Coordinates;
import org.example.base.model.Label;
import org.example.base.model.MusicBand;
import org.example.base.model.MusicGenre;
import org.example.command.AddIfMaxUserCommand;
import org.example.command.AddUserCommand;
import org.example.command.RemoveLowerUserCommand;
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

public class RemoveLowerController {
    private NetworkClient networkClient;
    private boolean isCanceled;

    public RemoveLowerController(NetworkClient networkClient) {
        this.networkClient = networkClient;
        isCanceled = true;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public String delete(String name, Integer x, long y, Long numberofParticipants, long albumsCount, String genre, double sales) {
        MusicBand musicBand = new MusicBand(1, name, new Coordinates(x, y), numberofParticipants, albumsCount, MusicGenre.valueOf(genre), new Label(sales));

        RemoveLowerUserCommand removeLowerUserCommand = new RemoveLowerUserCommand(networkClient);
        var response = removeLowerUserCommand.appExecute(musicBand);

        ErrorResponseHandler.checkForErrorResponse(response);

        return Localization.get("remove_lower");
    }

    public void close(JDialog dialog, boolean isCanceled) {
        this.isCanceled = isCanceled;
        dialog.dispose();
    }
}
