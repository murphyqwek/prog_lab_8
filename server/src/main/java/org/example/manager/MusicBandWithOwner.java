package org.example.manager;

import org.example.base.model.MusicBand;

/**
 * MusicBandWithOwner - класс для хранения MusicBand и владельца.
 *
 * @version 1.0
 */

public class MusicBandWithOwner {
    private MusicBand musicBand;
    private final String owner;

    public MusicBandWithOwner(MusicBand musicBand, String owner) {
        this.musicBand = musicBand;
        this.owner = owner;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }

    public void setMusicBand(MusicBand musicBand) {
        this.musicBand = musicBand;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return musicBand.toString() + "; Owner: " + owner;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;

        if(!(obj instanceof MusicBandWithOwner other)) return false;

        return musicBand.equals(other.musicBand) && owner.equals(other.owner);
    }


    @Override
    public int hashCode() {
        return musicBand.hashCode() | owner.hashCode();
    }
}
