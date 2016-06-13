package com.github.keeninvye.soundstack;

/**
 * Created by keeninvye on 6/13/16.
 */

import android.graphics.Bitmap;

public class Song {

    private String _name, _stream;
    private Bitmap _artwork;

    public Song(String name, String stream, Bitmap artwork) {
        _name = name;
        _stream = stream;
        _artwork = artwork;
    }

    public String getName() {
        return _name;
    }

    public String getStream() {
        return _stream;
    }

    public Bitmap getArtwork() {
        return _artwork;
    }
}
