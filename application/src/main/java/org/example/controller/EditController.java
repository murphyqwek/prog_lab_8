package org.example.controller;

import org.example.base.model.Coordinates;
import org.example.base.model.Label;
import org.example.base.model.MusicBand;
import org.example.base.model.MusicGenre;
import org.example.command.AddIfMaxUserCommand;
import org.example.command.AddUserCommand;
import org.example.command.UpdateUserCommand;
import org.example.network.NetworkClient;

import javax.swing.*;

/**
 * AddController - описание класса.
 *
 * @version 1.0
 */

public class EditController {
    private NetworkClient networkClient;
    private boolean isCanceled;
    public MusicBand musicBand;

    public EditController(NetworkClient networkClient, MusicBand musicBand) {
        this.networkClient = networkClient;
        this.musicBand = musicBand;
        isCanceled = true;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public String update(String name, Integer x, long y, Long numberofParticipants, long albumsCount, String genre, double sales) {
        MusicBand newMusicBand = new MusicBand(musicBand.getId(), name, new Coordinates(x, y), numberofParticipants, albumsCount, MusicGenre.valueOf(genre), new Label(sales));

        UpdateUserCommand updateUserCommand = new UpdateUserCommand(networkClient);

        var response = updateUserCommand.appExecute(musicBand.getId(), newMusicBand);

        return response.getMessage();
    }

    public void close(JDialog dialog, boolean isCanceled) {
        this.isCanceled = isCanceled;
        dialog.dispose();
    }
}
