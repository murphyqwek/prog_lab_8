package org.example.model;

import org.example.base.model.MusicBand;

import java.util.ArrayList;
import java.util.List;

/**
 * LocalStorage - описание класса.
 *
 * @version 1.0
 */

public class LocalStorage {
    private final List<MusicBand> localList;

    public LocalStorage() {
        localList = new ArrayList<>();
    }

    public void setMusicBands(Iterable<MusicBand> newMusicBands) {
        localList.clear();

        for(MusicBand band : newMusicBands) {
            localList.add(band);
        }
    }

    public List<MusicBand> getStorage() {
        return localList;
    }
}
