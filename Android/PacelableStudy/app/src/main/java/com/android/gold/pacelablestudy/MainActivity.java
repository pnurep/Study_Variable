package com.android.gold.pacelablestudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToActivity(View v){
        Intent intent = new Intent(this, SubActivity.class);
        Student student = new Student("홍길동", 3, 17, 1033337777);
        intent.putExtra("student", student);
        startActivity(intent);
    }


}
