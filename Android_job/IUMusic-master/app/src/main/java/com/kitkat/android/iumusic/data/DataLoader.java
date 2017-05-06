package com.kitkat.android.iumusic.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.kitkat.android.iumusic.domain.Artist;
import com.kitkat.android.iumusic.domain.Music;

/**
 * Created by apink on 2017-02-12.
 */

public class DataLoader {

    public static void loadData(Context context) {
        // ContentResolver for ContentProvider Access
        ContentResolver contentResolver = context.getContentResolver();

        // Music Database Content Uri
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // Music's Column
        String[] projections = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.COMPOSER,
                //MediaStore.Audio.Media.CONTENT_TYPE,
                MediaStore.Audio.Media.DATA
        };

        // Cursor
        // All Query Music
        Cursor cursor = contentResolver.query(
                contentUri,
                projections,
                null, null, null
        );

        while(cursor.moveToNext()) {
            Music music = new Music();

            music.setMusic_id(DataLoader.getValue(cursor, MediaStore.Audio.Media._ID));
            music.setAlbum_id(DataLoader.getValue(cursor, MediaStore.Audio.Media.ALBUM_ID));
            music.setAlbum(DataLoader.getValue(cursor, MediaStore.Audio.Media.ALBUM));
            music.setYear(DataLoader.getValue(cursor, MediaStore.Audio.Media.YEAR));
            music.setTrack(DataLoader.getValue(cursor, MediaStore.Audio.Media.TRACK));
            music.setTitle(DataLoader.getValue(cursor, MediaStore.Audio.Media.TITLE));
            music.setArtist(DataLoader.getValue(cursor, MediaStore.Audio.Media.ARTIST));
            music.setDuration(DataLoader.getValue(cursor, MediaStore.Audio.Media.DURATION));
            music.setComposer(DataLoader.getValue(cursor, MediaStore.Audio.Media.COMPOSER));
            //music.setContentType(DataLoader.getValue(cursor, MediaStore.Audio.Media.CONTENT_TYPE));
            music.setPath(DataLoader.getValue(cursor, MediaStore.Audio.Media.DATA));
            music.setUri(DataLoader.getUri(music.getMusic_id()));
            music.setAlbumUri(DataLoader.getAlbumUri(music.getAlbum_id()));

            Database.addMusic(music);
        }

        // Music Database Content Uri
        Uri artistUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;

        String[] artistProjections = {
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.ARTIST_KEY,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        };

        cursor = contentResolver.query(artistUri, artistProjections, null, null, null);

        while (cursor.moveToNext()) {
            Artist artist = new Artist();

            artist.setId(getValue(cursor, artistProjections[0]));
            artist.setArtist(getValue(cursor, artistProjections[1]));
            artist.setArtist_key(getValue(cursor, artistProjections[2]));
            artist.setNumber_of_albums(getValue(cursor, artistProjections[3]));
            artist.setNumber_of_tracks(getValue(cursor, artistProjections[4]));

            Database.addArtist(artist);

            Log.i("Artist", artist.getId()+ " / "+artist.getArtist()+ " / "+artist.getArtist_key()+ " / "+artist.getNumber_of_albums()+ " / "+artist.getNumber_of_tracks());
        }

        // if Cursor Ended, Must be Cursor is Closed!!
        cursor.close();
    }

    public static String getValue(Cursor cursor, String columnName) {
        int idx = cursor.getColumnIndex(columnName);
        return cursor.getString(idx);
    }

    public static String getGenre() {
        // MediaStore.Audio.Genres.getContentUriForAudioId();
        return "";
    }

    public static Uri getUri(String music_id) {
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return contentUri.withAppendedPath(contentUri, music_id);
    }


    public static Uri getAlbumUri(String album_id) {
        Uri uri = Uri.parse(("content://media/external/audio/albumart/" + album_id));
        return uri;
    }
}
