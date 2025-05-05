package org.example.model;

import org.example.base.model.MusicBand;
import org.example.base.model.MusicBandWithOwner;

import java.util.ArrayList;
import java.util.List;

/**
 * LocalStorageVisualize - описание класса.
 *
 * @version 1.0
 */

public class LocalStorageVisualize {
    private final List<MusicBandWithOwner> localList;

    public LocalStorageVisualize() {
        localList = new ArrayList<>();
    }

    public void setMusicBands(Iterable<MusicBandWithOwner> newMusicBands) {
        localList.clear();

        for(MusicBandWithOwner band : newMusicBands) {
            localList.add(band);
        }
    }

    public List<MusicBandWithOwner> getStorage() {
        return localList;
    }
}
