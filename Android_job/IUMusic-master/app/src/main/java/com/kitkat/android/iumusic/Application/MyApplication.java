package com.kitkat.android.iumusic.Application;

import android.app.Application;
import android.media.MediaPlayer;

/**
 * Application Class
 *
 * AndroidManifest 등록 시, 어플리케이션이 로드되면서 생성되는 객체
 * 앱 내의 모든 인스턴스가 접근 가능한 인스턴스
 *
 * Base class for maintaining global application state.
 * You can provide your own implementation by creating a subclass and
 * specifying the fully-qualified name of this subclass as the "android:name" attribute in your AndroidManifest.xml's <application> tag.
 * The Application class, or your subclass of the Application class,
 * is instantiated before any other class when the process for your application/package is created.
 *
 * <application
 *      android:name=".Application.MyApplication"
 *
 *  Application 인스턴스 사용하기
 *
 *      MyApplication myApplication = (MyApplication) getApplication();
 *      mediaPlayer = myApplication.mediaPlayer;
 *
 *  1. Application Class 에서 static 으로 선언한 전역변수는 언제 어디서나 접근,수정이 가능
 *  2. Application Class 에 작성된 메서드는 언제 어디서나 호출이 가능
 */

public class MyApplication extends Application {
    // Action
    public static final String ACTION_PLAY = "com.kitkat.android,iumusic.Action.PLAY";
    public static final String ACTION_PAUSE = "com.kitkat.android,iumusic.Action.PAUSE";
    public static final String ACTION_STOP = "com.kitkat.android,iumusic.Action.STOP";
    public static final String ACTION_NEXT = "com.kitkat.android,iumusic.Action.NEXT";
    public static final String ACTION_PREVIEW = "com.kitkat.android,iumusic.Action.PREVIEW";
    public static final String ACTION_TRACK = "com.kitkat.android,iumusic.Action.TRACK";

    // MediaPlayer Status
    public static boolean playStatus = false;
    public static boolean trackingStatus = false;

    // Flag
    public static final String TYPE_MUSIC = "MUSIC";
    public static final String TYPE_ARTIST = "ARTIST";
    public static final String TYPE_ALBUM = "ALBUM";
    public static final String TYPE_GENRE = "GENRE";
}
