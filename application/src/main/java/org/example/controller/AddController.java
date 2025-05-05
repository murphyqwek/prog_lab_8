package org.example.controller;

import org.example.base.model.Coordinates;
import org.example.base.model.Label;
import org.example.base.model.MusicBand;
import org.example.base.model.MusicGenre;
import org.example.command.AddIfMaxUserCommand;
import org.example.command.AddUserCommand;
import org.example.exception.InvalidMusicBandExcpetion;
import org.example.network.NetworkClient;

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
            var response = addIfMaxUserCommand.appExecute(newMusicBand).getMessage();
            isSaved = true;
            return response;
        }
        else {
            AddUserCommand addUserCommand = new AddUserCommand(networkClient);
            return addUserCommand.appExecute(newMusicBand).getMessage();
        }
    }

    public void close(JDialog dialog, boolean isCanceled) {
        this.isCanceled = isCanceled;
        dialog.dispose();
    }
}
