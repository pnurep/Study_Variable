package com.example.gold.realmintroexample;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Gold on 2017. 4. 9..
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //이니셜라이즈 렘
        Realm.init(this);
    }
}
