package org.example.base.response;

import org.example.base.model.MusicBand;

import java.util.List;


/**
 * ServerResponseWithMusicBandList - описание класса.
 *
 * @version 1.0
 */

public class ServerResponseWithMusicBandList extends ServerResponse {
    private final List<MusicBand> musicBandList;

    public ServerResponseWithMusicBandList(ServerResponseType type, String message, List<MusicBand> musicBandList) {
        super(type, message);

        if(musicBandList == null) {
            throw new NullPointerException("musicBandList is null");
        }

        this.musicBandList = musicBandList;
    }

    public List<MusicBand> getMusicBandList() {
        return musicBandList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        musicBandList.stream().forEach(mb -> builder.append(mb.toString()).append("\n"));
        return "ServerResponse {type: " + getType() + ", message: " + getMessage() + " MusicBands: " + builder.toString() + "}";
    }
}
