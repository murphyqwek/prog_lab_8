package org.example.base.response;

import org.example.base.model.MusicBandWithOwner;

import java.util.List;

/**
 * ServerResponseMusicBandOwners - описание класса.
 *
 * @version 1.0
 */

public class ServerResponseMusicBandOwners extends ServerResponse {
    private List<MusicBandWithOwner> musicBandWithOwners;

    public ServerResponseMusicBandOwners(ServerResponseType type, String message, List<MusicBandWithOwner> musicBandWithOwners) {
        super(type, message);
        this.musicBandWithOwners = musicBandWithOwners;
    }

    public List<MusicBandWithOwner> getMusicBandWithOwners() {
        return musicBandWithOwners;
    }
}
