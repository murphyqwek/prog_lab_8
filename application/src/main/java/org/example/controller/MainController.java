package org.example.controller;

import org.example.base.model.MusicBand;
import org.example.base.response.ServerResponseType;
import org.example.command.ShowUserCommand;
import org.example.exception.ExecuteAppCommandExcpetion;
import org.example.model.LocalStorage;
import org.example.network.NetworkClient;

import java.util.List;

/**
 * MainController - описание класса.
 *
 * @version 1.0
 */

public class MainController {
    private final NetworkClient networkClient;
    private final LocalStorage localStorage;

    public MainController(NetworkClient networkClient) {
        this.networkClient = networkClient;
        this.localStorage = new LocalStorage();
    }

    public void updateLocalStorage() {
        ShowUserCommand showUserCommand = new ShowUserCommand(networkClient);

        var response = showUserCommand.appExecute();

        if(response.getType() != ServerResponseType.SUCCESS) {
            throw new ExecuteAppCommandExcpetion(response.getMessage());
        }

        localStorage.setMusicBands(response.getMusicBandList());
    }

    public Object[][] getMusicBandsToDisplay() {
        List<MusicBand> musicBands = localStorage.getStorage();

        Object[][] result = new Object[musicBands.size()][];

        for(int i = 0; i < musicBands.size(); i++) {
            MusicBand musicBand = musicBands.get(i);
            result[i] = getMusicBandToDisplay(musicBand);
        }

        return result;
    }

    private Object[] getMusicBandToDisplay(MusicBand musicBand) {
        return new Object[] {
                musicBand.getId(),
                musicBand.getName(),
                musicBand.getCoordinates().getX(),
                musicBand.getCoordinates().getY(),
                musicBand.getCreationDate().getTime(),
                musicBand.getAlbumsCount(),
                musicBand.getNumberOfParticipants(),
                musicBand.getGenre().name(),
                musicBand.getLabel().getSales()
        };
    }
}
