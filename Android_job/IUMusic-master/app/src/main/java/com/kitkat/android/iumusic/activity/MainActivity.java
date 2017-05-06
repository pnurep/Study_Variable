package com.kitkat.android.iumusic.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;

import com.kitkat.android.iumusic.Util.Permission;
import com.kitkat.android.iumusic.data.DataLoader;
import com.kitkat.android.iumusic.data.Database;
import com.kitkat.android.iumusic.domain.Music;
import com.kitkat.android.iumusic.fragment.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.kitkat.android.iumusic.R;
import com.kitkat.android.iumusic.fragment.ListFragment;

import static com.kitkat.android.iumusic.Application.MyApplication.TYPE_ARTIST;
import static com.kitkat.android.iumusic.Application.MyApplication.TYPE_MUSIC;
import static com.kitkat.android.iumusic.Application.MyApplication.playStatus;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // Permission
    private static final String[] permissionArr = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQ_PERMISSION = 1;

    // View
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 앱을 껏다가 플레이어가 실행중이면 일단 PlayActivity 로 이동
        if(playStatus) {
            Intent intent = new Intent(this, PlayActivity.class);
            startActivity(intent);
        }

        // SDK Version Check
        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M)
            if(Permission.checkPermission(this, permissionArr, REQ_PERMISSION))
                init();
        else
            init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void init() {
        // 볼륨 조절 버튼으로 미디어 음량만 조절하기 위한 설정
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        DataLoader.loadData(getApplicationContext());
        viewSetting();
    }

    public void viewSetting() {
        // 화면의 Toolbar Setting
        // app_bar_main,xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Floating Button Setting
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener((v) -> {
                Intent intent = new Intent(this, PlayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
        });

        // NavigationDrawer Setting
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // content_main.xml Setting
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.music)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.artist)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.album)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.genre)));
        // tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.black, null));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        if(tabLayout.getTabCount() > 4)
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        else
            tabLayout.setTabMode(TabLayout.MODE_FIXED);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragemt(ListFragment.newInstance(3, TYPE_MUSIC));
        pagerAdapter.addFragemt(ListFragment.newInstance(1, TYPE_ARTIST));
        pagerAdapter.addFragemt(ListFragment.newInstance(1, TYPE_MUSIC));
        pagerAdapter.addFragemt(ListFragment.newInstance(1, TYPE_MUSIC));

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);

        // Listener for TabLayout << >> ViewPager
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Toolbar OptionMenu Setting Method
    // menu 의 item 이 하나이면, 메뉴 하나만 표시, 둘 이상이면 ... 표시
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Music Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 검색어 입력 완료시
            @Override
            public boolean onQueryTextSubmit(String query) {
                Music music;
                String title;
                String artist;

                for(int i=0; i<Database.musicListSize(); i++) {
                    music = Database.getMusic(i);
                    title = music.getTitle();
                    artist = music.getArtist();

                    if(title.contains(query))
                        Log.i("Search","=========================Title = "+title);
                    if(artist.contains(query))
                        Log.i("Search","=========================Artist = "+artist);
                }

                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", query);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                return false;
            }

            // 검색어 입력 시
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_search:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_music) { viewPager.setCurrentItem(0); }
        else if (id == R.id.nav_artist) { viewPager.setCurrentItem(1); }
        else if (id == R.id.nav_album) { viewPager.setCurrentItem(2); }
        else if (id == R.id.nav_genre) { viewPager.setCurrentItem(3); }
        else if (id == R.id.nav_share) { }
        else if (id == R.id.nav_send) { }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
