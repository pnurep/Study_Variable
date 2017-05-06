package com.kitkat.android.iumusic.data;

import com.kitkat.android.iumusic.domain.Artist;
import com.kitkat.android.iumusic.domain.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apink on 2017-02-12.
 */

public class Database {
    // Total Music Music
    private static List<Music> musicList = new ArrayList<>();
    private static List<Artist> artistList = new ArrayList<>();

    public static void addMusic(Music music) {
        musicList.add(music);
    }
    public static void addArtist(Artist artist) { artistList.add(artist); }

    public static void removeMusic(int idx) {
        musicList.remove(idx);
    }
    public static void removeArtist(int idx) {
        musicList.remove(idx);
    }

    public static void removeMusic(Music music) {
        musicList.remove(music);
    }
    public static void removeArtist(Artist artist) {
        musicList.remove(artist);
    }

    public static Music getMusic(int idx) { return musicList.get(idx); }
    public static Artist getArtist(int idx) { return artistList.get(idx); }

    public static int musicListSize() { return musicList.size(); }
    public static int artistListSize() { return artistList.size(); }
}
