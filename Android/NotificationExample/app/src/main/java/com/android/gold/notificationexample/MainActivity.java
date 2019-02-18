package com.android.gold.notificationexample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

	Button btn1;
	NotificationManager managerCompat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		managerCompat = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


		btn1 = (Button) findViewById(R.id.btn_1);
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setupNotification();
			}
		});
	}

	private void setupNotification() {

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder =
				new NotificationCompat.Builder(this)
				.setSmallIcon(android.R.drawable.stat_notify_chat)
				.setContentTitle("테에스트")
				.setTicker("상태바 한줄 메시지")
				.setContentText("내-용")
				.setDefaults(NotificationCompat.DEFAULT_SOUND)
//				.setContentIntent(pendingIntent)
				.setPriority(NotificationCompat.PRIORITY_MAX);

		managerCompat.notify(0, builder.build());

	}
}
