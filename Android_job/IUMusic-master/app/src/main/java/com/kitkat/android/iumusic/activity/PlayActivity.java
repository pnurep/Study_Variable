package com.kitkat.android.iumusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kitkat.android.iumusic.R;
import com.kitkat.android.iumusic.Util.Util;
import com.kitkat.android.iumusic.data.Database;
import com.kitkat.android.iumusic.domain.Music;
import com.kitkat.android.iumusic.media.MediaManager;
import com.kitkat.android.iumusic.media.MediaService;
import com.kitkat.android.iumusic.media.Observer;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_NEXT;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_PAUSE;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_PLAY;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_PREVIEW;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_STOP;
import static com.kitkat.android.iumusic.Application.MyApplication.ACTION_TRACK;
import static com.kitkat.android.iumusic.Application.MyApplication.playStatus;
import static com.kitkat.android.iumusic.Application.MyApplication.trackingStatus;

/**
 * Activity Back Stack
 *
 * 액티비티는 액티비티 스택으로 관리
 * 액티비티 스택 하나는 하나의 TASK로 관리
 * 액티비티를 차곡차곡 쌓아두었다가 가장 상위에 있던 액티비티가 없어지면 이전의 액티비티를 다시 실행하도록 도와준다.
 *
 * Activity Back Stack 메커니즘
 *
 *      - 새로운 액티비티를 만들어 AndroidManifest.xml 파일에 등록하면 startActivity() 메소드를 이용해 실행 가능
 *      - 새로운 액티비티가 화면에 띄워지면, 이전에 있던 액티비티는 액티비티 스택에 저장되고 (Background), 새로운 엑티비티가 화면에 보이는 구조 (Foreground)
 *      - 화면에 보이던 액티비티가 없어지면 액티비티 스택의 가장 위에 있던 액티비티가 화면 싱에 보이면서 재 실행
 *      - 하지만 동일한 액티비티를 여러번 실행하면, 동일한 액티비티 여러 개가 스택에 쌓이게 되고,
 *        동시 데이터 접근이나 리소스를 여러번 사용하는 문제가 발생할 수 있다.
 *
 * Activity Intent Flag
 *
 *      - FLAG_ACTIVITY_SINGLE_TOP  액티비티를 생성할 때 이미 생성된 액티비티가 있으면 그 액티비티를 그대로 사용하라는 플래그
 *
 *      - onNewIntent()             액티비티가 이미 메모리 상의 객체로 만들어져 있는 경우는 액티비티를 다시 띄우더라도
 *                                  onCreate() 메소드가 호출되지 않기 때문에, 인텐트를 받아 처리할 수 있게 콜백되는 메소드
 *
 *      - FLAG_ACTIVITY_NO_HISTORY  처음 이후에 실행되는 액티비티는 액티비티 스택에 추가되지 않는다.
 *                                  (Back Key 사용 시 유용)
 *
 *      - FLAG_ACTIVITY_CLEAR_TOP   스택에서 이 액티비티에 있는 다른 액티비티 모두를 종료하는 플래그
 *
 *      - FLAG_ACTIVITY_NEW_TASK    새로운 TASK의 액티비티 스택을 만들고 새로 만든 스택에 저장
 *
 */
public class PlayActivity extends AppCompatActivity implements Observer {
    private static final String TAG = "LifeCycle";
    private static final int WHAT_TRACKING = 1;

    // Handler
    public static TrackingHandler trackingHandler;

    // View
    private ConstraintLayout constraintLayout;
    private ViewPager imgPager;
    private TextView title, album, artist, time, duration;
    private SeekBar tracker;
    private ImageButton btnPre, btnPlay, btnNext;
    private ImgPagerAdapter imgPagerAdapter;

    // Music
    private Music music;

    // MediaManager
    private MediaManager mediaManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Log.i(TAG, "onCreate().....");

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume().....");
        trackingStatus = true;
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop().....");
        super.onStop();
        trackingStatus = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy().....");
        trackingStatus = false;
        mediaManager.unregisterObserver(this);
    }

    // Activity 상태 저장 시 호출되는 Callback Method 재 정의
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState().....");
    }

    // Activity 상태 복원 시 호출되는 Callback Method 재 정의
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState().....");
    }

    // Observer Callback Method
    @Override
    public void update(String action) {
        if (action.equals(ACTION_PLAY))
            btnPlay.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);

        else if (action.equals(ACTION_PAUSE))
            btnPlay.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);

        else if (action.equals(ACTION_PREVIEW))
            dataSetting(mediaManager.getPos());

        else if (action.equals(ACTION_NEXT))
            dataSetting(mediaManager.getPos());

        else if (action.equals(ACTION_STOP)) {
            finish();
        }
    }

    private void init() {
        mediaManager = MediaManager.getInstance();
        mediaManager.registerObserver(this);

        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            mediaManager.setPos(bundle.getInt("pos"));
            requestService(ACTION_TRACK);
        }

        trackingHandler = new TrackingHandler();
        trackingStatus = true;

        viewSetting();
        dataSetting(mediaManager.getPos());
    }

    private void viewSetting() {
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint);
        title = (TextView) findViewById(R.id.title);
        album = (TextView) findViewById(R.id.album);
        artist = (TextView) findViewById(R.id.artist);
        time = (TextView) findViewById(R.id.time);
        duration = (TextView) findViewById(R.id.duration);

        tracker = (SeekBar) findViewById(R.id.tracker);
        tracker.setOnSeekBarChangeListener(onSeekBarChangeListener);

        btnPre = (ImageButton) findViewById(R.id.btnPre);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnNext = (ImageButton) findViewById(R.id.btnNext);

        btnPre.setOnClickListener(onClickListener);
        btnPlay.setOnClickListener(onClickListener);
        btnNext.setOnClickListener(onClickListener);

        imgPager = (ViewPager) findViewById(R.id.imgPager);
        imgPagerAdapter = new ImgPagerAdapter();
        imgPager.setAdapter(imgPagerAdapter);
        imgPager.setPageTransformer(false, pageTransformer);
        imgPager.addOnPageChangeListener(onPageChangeListener);

        if(playStatus)
            btnPlay.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
    }

    public void dataSetting(int pos) {
        music = Database.getMusic(pos);

        title.setText(music.getTitle());
        // Text 흐
        title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        title.setSingleLine(true);
        title.setMarqueeRepeatLimit(5);
        title.setSelected(true);

        album.setText(music.getAlbum());
        artist.setText(music.getArtist());
        imgPager.setCurrentItem(pos, true);

        long t = Long.parseLong(music.getDuration());
        duration.setText(Util.millToTime(t));
        tracker.setMax((int)t);
    }

    public void requestService(String action) {
        Intent intent = new Intent(this, MediaService.class);
        intent.setAction(action);
        startService(intent);
    }

    public class TrackingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("Handler", "Tracking.....");

            switch (msg.what) {
                case WHAT_TRACKING:
                    tracker.setProgress(msg.arg1);
                    break;
            }
        }
    }

    class ImgPagerAdapter extends PagerAdapter {
        private LayoutInflater inflater;

        public ImgPagerAdapter() {
            inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        // listView 의 getView 와 같은 역할
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // return super.instantiateItem(container, position);
            View view = inflater.inflate(R.layout.img_item, container, false);
            Music item = Database.getMusic(position);

            ImageView img = (ImageView) view.findViewById(R.id.img);

            Glide.with(getApplicationContext()).load(item.getAlbumUri())
                    .placeholder(R.mipmap.ic_launcher)
                    .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                    .into(img);

            container.addView(view);
            return view;
        }

        // 데이터 총 개수
        @Override
        public int getCount() {
            return Database.musicListSize();
        }

        // instantiateItem 에서 리턴된 Object 가 View 가 맞는지를 확인하는 함수
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        // 화면에서 사라진 뷰를 메모리에서 제거하기 위한 함수
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mediaManager.setPos(position);
            dataSetting(position);
            // requestService(ACTION_TRACK);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    View.OnClickListener onClickListener = (v) -> {
            switch (v.getId()) {
                case R.id.btnPre:
                    if(mediaManager.getPos() > 0)
                        requestService(ACTION_PREVIEW);
                    break;

                case R.id.btnPlay:
                    if(playStatus)
                        requestService(ACTION_PAUSE);
                    else
                        requestService(ACTION_PLAY);
                    break;

                case R.id.btnNext:
                    if(mediaManager.getPos() < Database.musicListSize())
                        requestService(ACTION_NEXT);
                    break;
            }
    };

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            time.setText(Util.millToTime(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int t = seekBar.getProgress();
            mediaManager.seek(t);
            seekBar.setProgress(t);
            time.setText(Util.millToTime(t));
        }
    };

    ViewPager.PageTransformer pageTransformer = (page, position) -> {
            float normalizedposition = Math.abs( 1 - Math.abs(position) );

            page.setAlpha(normalizedposition);  //View의 투명도 조절
            page.setScaleX(normalizedposition/2 + 0.5f); //View의 x축 크기조절
            page.setScaleY(normalizedposition/2 + 0.5f); //View의 y축 크기조절
            page.setRotationY(position * 80); //View의 Y축(세로축) 회전 각도  
    };
}

/*
    // Use SharedPreference Instance
    // private static final String PREF_ID = "id";
    // private static final int mode = Activity.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // 이전 상태 복원
        SharedPreferences preferences = getSharedPreferences(PREF_ID, mode);

        if(preferences != null && preferences.contains("key"))
            pos = preferences.getInt("key", 0);
        else
            pos = bundle.getInt("pos");

        init();
    }

     @Override
    protected void onDestroy() {
        super.onDestroy();

        // 현재 상태 저장
        SharedPreferences preferences = getSharedPreferences(PREF_ID, mode);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("key", pos);
        editor.commit();
    }
 */
