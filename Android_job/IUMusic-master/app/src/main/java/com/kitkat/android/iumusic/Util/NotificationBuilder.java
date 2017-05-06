package com.kitkat.android.iumusic.Util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.kitkat.android.iumusic.R;
import com.kitkat.android.iumusic.activity.PlayActivity;
import com.kitkat.android.iumusic.domain.Music;

import java.io.FileNotFoundException;

public class NotificationBuilder {

    public static Notification createNotification(Context context, Music music) {
        // 1, Notification.builder 정의
        // Notification.Builder 는 4.1부터만 되기 때문에 그 아래 버전은 사용이 불가능
        // 그래서 호환성을 위해 NotificationCompat.Builder 라는 것이 존재
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_headset_black_24dp)
                    .setContentTitle("IUMUsic")
                    .setContentText("PLaying.....");

        // Notification's Custom Content Layout (RemoteViews) Setting
        // builder.setContent(remoteViews);
        builder.setCustomBigContentView(createRemoteView(context, music)); // 확장 보기 레이아웃

        // 2. Notification Click 시 보낼 Intent 정의
        Intent intent = new Intent(context, PlayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // 3. 클릭할 때까지 액티비티 실행을 보류하고 있는 PendingIntent 객체 생성
        // PendingIntent 객체는 다른 응용 프로그램으로 전달 될 수 있으므로 사용자가 나중에 해야하는 작업을 intent 에 담아 수행 할 수 있다.
        PendingIntent pendingIntent =
                // stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // 4. Notification Builder 에 PendingIntent Setting
        builder.setContentIntent(pendingIntent);

        // 알림 자체를 생성하려면 NotificationCompat.Builder.build()를 호출. 이는 사양이 포함된 Notification 객체를 반환
        return builder.build();
    }

    private static RemoteViews createRemoteView(Context context, Music music) {
        // Custom Notification Remote View
        // 사용자 지정 알림 레이아웃에 사용할 수 있는 높이는 알림 보기에 따라 다릅니다.
        // 일반 보기 레이아웃은 64dp로 제한되어 있으며 확장 보기 레이아웃은 256dp로 제한
        Bitmap img = null;
        try {
            img = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(music.getAlbumUri()));
        } catch (FileNotFoundException e) {
            img = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.noti_custom);
        remoteViews.setImageViewBitmap(R.id.notiImg, img);
        remoteViews.setTextViewText(R.id.notiName, context.getResources().getString(R.string.app_name));
        remoteViews.setTextColor(R.id.notiName, Color.BLACK);
        remoteViews.setTextViewText(R.id.notiTitle, music.getTitle());
        remoteViews.setTextColor(R.id.notiTitle, Color.BLACK);
        remoteViews.setTextViewText(R.id.notiArtist, music.getArtist());
        remoteViews.setTextColor(R.id.notiArtist, Color.BLACK);

        return remoteViews;
    }

    /*
        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
    */
}
