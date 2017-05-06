package com.kitkat.android.iumusic.domain;

import android.net.Uri;

import java.util.List;

public class Artist extends Common {
    private String id;
    private String artist;
    private String artist_key;
    private String album_id;
    private Uri albumUri;
    private String number_of_albums;
    private String number_of_tracks;
    private List<Music> musics;

    @Override
    public String getTitle() {
        return artist;
    }

    @Override
    public String getAlbum() {
        return number_of_albums;
    }

    @Override
    public String getArtist() {
        return number_of_tracks;
    }

    @Override
    public Uri getAlbumUri() {
        return albumUri;
    }

    public void setAlbumUri(Uri albumUri) {
        this.albumUri = albumUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist_key() {
        return artist_key;
    }

    public void setArtist_key(String artist_key) {
        this.artist_key = artist_key;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getNumber_of_albums() {
        return number_of_albums;
    }

    public void setNumber_of_albums(String number_of_albums) {
        this.number_of_albums = number_of_albums;
    }

    public String getNumber_of_tracks() {
        return number_of_tracks;
    }

    public void setNumber_of_tracks(String number_of_tracks) {
        this.number_of_tracks = number_of_tracks;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }
}
