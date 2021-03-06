package com.kitkat.android.iumusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.kitkat.android.iumusic.R;

public class IntroActivity extends AppCompatActivity {
    // Splash Activity Time Length
    private final int SPLASH_DISPLAY_LENGTH = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        loadMain();
    }

    // After SPLASH_DISPLAY_LENGTH, 이 Activity finish 후 MainActivity 호출
    private void loadMain() {

        /** postDelayed(Runnable r, long delayMills)
         *
         *  delayMills 지연 후 r 실행
         *
         *  @Param Runnable r       쓰레딩을 위한 Runnable 객체
         *  @Param long delayMills  쓰레딩 지연 시간
         */
        new Handler().postDelayed(() -> { // Lambda Expression
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                // Apply Animation
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}
