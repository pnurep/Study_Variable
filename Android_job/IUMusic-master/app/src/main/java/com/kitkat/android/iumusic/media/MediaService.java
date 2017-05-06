package com.kitkat.android.iumusic.media;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.kitkat.android.iumusic.R;
import com.kitkat.android.iumusic.activity.MainActivity;
import com.kitkat.android.iumusic.data.Database;
import com.kitkat.android.iumusic.domain.Music;

import java.io.IOException;

import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_NEXT;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_PAUSE;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_PLAY;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_PREVIEW;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_STOP;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_TRACK;
import static com.kitkat.android.iumusic.Application.MyApplication.playStatus;

/**
 * MediaPlayer Foreground Service
 */
public class MediaService extends Service {
    private static final int ONGOING_NOTIFICATION_ID = 1;
    private static final String SESSION_TAG = "Music";

    private MediaManager mediaManager;
    private MediaSessionCompat mediaSession;
    private MediaControllerCompat mediaController;

    private Notification notification;

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    public void handleIntent(Intent intent) {
        if(intent == null || intent.getAction() == null)
            return;

        String action = intent.getAction();

        if (action.equals(ACTION_PLAY))
            mediaController.getTransportControls().play();
        else if (action.equals(ACTION_PAUSE))
            mediaController.getTransportControls().pause();

        else if (action.equals(ACTION_PREVIEW))
            mediaController.getTransportControls().skipToPrevious();

        else if (action.equals(ACTION_NEXT))
            mediaController.getTransportControls().skipToNext();

        else if (action.equals(ACTION_TRACK)) {
            mediaManager.track();
            notification = createNotification(getApplicationContext());
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, notification);
        }

        else if (action.equals(ACTION_STOP)) {
            mediaManager.stop();
            stopForeground(true);
            stopSelf();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaSession.release();
        mediaManager.release();
    }

    public void init() {
        mediaManager = MediaManager.getInstance();
        mediaManager.init();

        mediaSession = new MediaSessionCompat(this, SESSION_TAG);
        mediaSession.setActive(true);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setCallback(mediaSessionCallback);

        try {
            // Token 은 유효성을 검증하는 Key (일회성 입장표)
            mediaController = new MediaControllerCompat(getApplicationContext(), mediaSession.getSessionToken());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        startForeground(ONGOING_NOTIFICATION_ID, createNotification(getApplicationContext()));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Notification createNotification(Context context) {
        Notification noti;
        NotificationCompat.Builder builder;
        Bitmap albumImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_headset_black_24dp);
        Music music = Database.getMusic(mediaManager.getPos());

        try {
            albumImg = MediaStore.Images.Media.getBitmap(getContentResolver(), music.getAlbumUri());
        } catch (IOException e) {
            e.printStackTrace();
        }

        NotificationCompat.Action playAction = playStatus ?
                createAction(R.drawable.ic_pause_black_24dp, "Pause", ACTION_PAUSE) :
                createAction(R.drawable.ic_play_arrow_black_24dp, "Play", ACTION_PLAY);

        builder = new NotificationCompat.Builder(context);

        // Set the Notification style
        // import android.support.v7.app.NotificationCompat 에 MediaStyle 있음
        builder.setStyle(new NotificationCompat.MediaStyle()
                .setMediaSession(mediaSession.getSessionToken())
                .setShowActionsInCompactView(0 ,1, 2));

        builder.setPriority(Notification.PRIORITY_DEFAULT)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setCategory(Notification.CATEGORY_TRANSPORT)

                // Notification Content Setting
                .setColor(Color.DKGRAY)
                .setSmallIcon(R.drawable.ic_headset_black_24dp)
                .setLargeIcon(albumImg)
                .setContentTitle(music.getTitle())
                .setContentText(music.getArtist())

                 // Hide the timestamp
                .setShowWhen(false)

                // Set Ticker (상단 상태표시줄에 출력될 문자열)
                .setTicker(getResources().getString(R.string.app_name))

                // when notified LED lighting Setting
                // .setLights(0xff00ff00, 2000, 2000)

                // Action Setting
                .addAction(createAction(R.drawable.ic_skip_previous_black_24dp, "Preview", ACTION_PREVIEW))
                .addAction(playAction)
                .addAction(createAction(R.drawable.ic_skip_next_black_24dp, "Next", ACTION_NEXT))
                .addAction(createAction(R.drawable.ic_close_black_24dp, "Stop", ACTION_STOP));

        // 2. Notification Click 시 보낼 Intent 정의
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // 3. 클릭할 때까지 액티비티 실행을 보류하고 있는 PendingIntent 객체 생성
        // PendingIntent 객체는 다른 응용 프로그램으로 전달 될 수 있으므로 사용자가 나중에 해야하는 작업을 intent 에 담아 수행 할 수 있다.
        // Context 가 없는 객체에서 시스템 리소스를 사용하기 위해 Context 와 Intent 를 담아서 실행
        PendingIntent pendingIntent =
                // stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // 4. Notification Builder 에 PendingIntent Setting
        builder.setContentIntent(pendingIntent);

        // 알림 자체를 생성하려면 NotificationCompat.Builder.build()를 호출. 이는 사양이 포함된 Notification 객체를 반환
        noti = builder.build();

        noti.flags = Notification.FLAG_AUTO_CANCEL;
        return noti;
    }

    private NotificationCompat.Action createAction(int iconResId, String title, String action) {
        Intent intent = new Intent(getApplicationContext(), MediaService.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        return new NotificationCompat.Action.Builder(iconResId, title, pendingIntent).build();
    }

    MediaSessionCompat.Callback mediaSessionCallback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            super.onPlay();
            mediaManager.play();
            notification = createNotification(getApplicationContext());
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, notification);
        }

        @Override
        public void onPause() {
            super.onPause();
            mediaManager.pause();
            notification = createNotification(getApplicationContext());
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, notification);
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            mediaManager.next();
            notification = createNotification(getApplicationContext());
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, notification);
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            mediaManager.preview();
            notification = createNotification(getApplicationContext());
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, notification);
        }
    };
}

/** Handler
 *
 * Handler that receives messages from the thread
 *
 * 안드로이드 UI는 기본적으로 싱글 스레드 모델로 작동하므로, 이 영향을 고려해 개발하지 않으면 애플리케이션 성능 저하
 * 따라서, 메인 스레드에서 긴 작업을 하는 것을 피하기 위해 여분의 스레드와 핸들러, 루퍼를 사용해야 한다.
 *
 * - 동기화 이슈
 * - 메인쓰레드 만이 UI를 조작할 수 있다!
 * - 메인쓰레드는 컴포넌트, 새로운 화면 관리를 위한 Looper, Message Queue 기본적으로 포함
 *
 * Looper (Message Queue Management)
 *      Message Queue를 포함하고, 무한루프 방식으로 Message or Runnable 인스턴스를 차례로 꺼내 Handler로 전달
 *
 * Message Queue
 *      전달받은 Message를 저장하는 Queue (FIFO, 선입 선출)
 *
 * Handler (Message Process)
 *      스레드의 Message Queue 와 연계하여 Message or Runnable 객체를 처리하는 메시지 서버 객체
 *
 *      1. 다른 Thread로 부터 메시지를 받아서 Message Queue에 전달
 *
 *      2. Looper로 부터 받은 Message 실행, 처리
 *
 *      3. 새로 Handler 객체를 만든 경우 이를 만든 스레드와 해당 스레드의 Message Queue에 자동 바인드!
 *         (핸들러 인스턴스 생성 위치가 새로운 쓰레드 안이 아니면 메인 쓰레드에 바인딩)
 *
 *      4. 새로 만든 Thread의 경우, Looper가 없기 때문에, 다음 메소드로 run() 메소드 내에서 Looper 생성 및 루핑
 *         Looper.prepare(), Lopper.loop()
 *
 *         Thread t = new Thread(new Runnable(){
 *              @Override
 *              public void run() {
 *                  Looper.prepare();
 *                  handler = new Handler(); // 해당 핸들러는 이 쓰레드에 바인드
 *                  Looper.loop();
 *              }
 *         });
 *         t.start();  
 *
 * Handler Variable
 *      WHAT     메시지의 구분 코드
 *      Arg1     메시지에 담을 첫번째 데이터
 *      Arg2     메시지에 담을 두번째 데이터
 *      Runnable r  인자인 Runnable 객체를 실행시킨다.
 *
 * Handler Method
 *      void             handleMessage(Message msg)  Looper가 Message Queue에서 꺼내준 Message나 Runnable 객체 처리 (상속 시 구현 필수)
 *      final boolean    post(Runnable r)            Message Queue에 Runnable r을 전달 ()
 *      final boolean    sendMessage(Message msg)    Message Queue에 Message msg를 전달
 *      final boolean    sendMessageDelayed(Message msg, long delayMillis) delayMillis 만큼 지연 후 Message Queue에 Message msg를 전달
 *
 * Message
 *      Message란 스레드 간 통신할 내용을 담는 객체, Handler를 통해 보낼 수 있다.
 *      일반적으로 Message가 필요할 때 새 Message 객체를 생성하면 성능 이슈가 생길 수 있으므로
 *      안드로이드가 시스템에 만들어 둔 Message Pool의 객체를 재사용
 *      obtain() 메서드는 빈 Message 객체를, obtain(Handler h, int what …)은 목적 handler와 다른 인자들을 담은 Message 객체를 리턴
 *
 * HandlerThread
 *      서브 스레드는 Java의 스레드를 사용하기 때문에 안드로이드에서 도입한 Looper를 기본으로 가지지 않는다.
 *      이 같은 불편함을 개선하기 위해 생성할 때 Looper를 자동으로 보유한 클래스를 제공
 *
 * Handler 와 Looper 를 사용한다면 작동 원리를 고려해야 하며 구현을 직접 해야 하고 코드가 복잡해져서 가독성을 저해한다는 단점이 있지만
 * 그만큼 개발 범위가 자유롭다. 또한 UI 스레드에서만 작업하지 않아도 되므로 보다 많은 자율성을 가지고 코드를 제어하기를 원한다면
 * Handler 나 HandlerThread 사용을 고려해 보아도 된다.
 */