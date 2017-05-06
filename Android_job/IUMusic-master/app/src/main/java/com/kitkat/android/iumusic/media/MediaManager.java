package com.kitkat.android.iumusic.media;

import android.media.MediaPlayer;
import android.os.Message;

import com.kitkat.android.iumusic.activity.PlayActivity;
import com.kitkat.android.iumusic.data.Database;
import com.kitkat.android.iumusic.domain.Music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_NEXT;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_PAUSE;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_PLAY;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_PREVIEW;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_STOP;
import static com.kitkat.android.iumusic.Application.MyApplication.playStatus;
import static com.kitkat.android.iumusic.Application.MyApplication.trackingStatus;

public class MediaManager {
    private static final int WHAT_TRACKING = 1;

    private static MediaManager mediaManager = null;
    private MediaPlayer mediaPlayer;

    private TrackingThread trackingThread;

    private List<Observer> observers;

    private Music music;
    private int pos;

    private MediaManager() {
        observers = new ArrayList<>();
    }

    public static MediaManager getInstance() {
        if(mediaManager == null)
            mediaManager = new MediaManager();

        return mediaManager;
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    // All Observer's Notification -> observers's update Method Callback!!
    public void notification(String action) {
        for(Observer observer : observers) {
            observer.update(action);
        }
    }

    public void init() {
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setLooping(false); // 반복여부
            mediaPlayer.setOnCompletionListener((mp) -> {
                if(playStatus) {
                    next();
                    play();
                }
            });

            // MediaPlayer 의 준비를 지켜보는 리스너
            mediaPlayer.setOnPreparedListener((mp) -> {
                if(playStatus)
                    mediaPlayer.start();
            });
        }
    }

    public void play() {
        mediaPlayer.start();
        playStatus = true;

        if(trackingThread == null) {
            trackingThread = new TrackingThread();
            trackingThread.start();
        }

        notification(ACTION_PLAY);
    }

    public void pause() {
        mediaPlayer.pause();
        playStatus = false;

        if(trackingThread != null)
            trackingThread = null;

        notification(ACTION_PAUSE);
    }

    public void stop() {
        if (mediaPlayer != null) {
            pause();
            mediaPlayer.reset();
            mediaPlayer.stop();
        }

        notification(ACTION_STOP);
    }

    public void next() {
        if(pos < Database.musicListSize()) {
            pos++;
            track();
        }

        notification(ACTION_NEXT);
    }

    public void preview() {
        if(pos > 0) {
            pos--;
            track();
        }

        notification(ACTION_PREVIEW);
    }

    public void seek(int msec) {
        mediaPlayer.seekTo(msec);
    }

    public void track() {
        try {
            mediaPlayer.reset();
            music = Database.getMusic(pos);
            mediaPlayer.setDataSource(music.getPath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void release() {
        // The service is no longer used and is being destroyed
        mediaPlayer.release();
        mediaPlayer = null;
        trackingThread = null;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
    
    public int getPos() {
        return pos;
    }

    class TrackingThread extends Thread {
        @Override
        public void run() {
            super.run();

            while(playStatus) {
                if(trackingStatus) {
                    Message msg = Message.obtain();
                    msg.what = WHAT_TRACKING;
                    msg.arg1 = mediaPlayer.getCurrentPosition();
                    PlayActivity.trackingHandler.sendMessage(msg);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
