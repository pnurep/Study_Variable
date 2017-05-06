package com.android.gold.pacelablestudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);


        Intent intent = getIntent();
        Student student = intent.getParcelableExtra("student");
        String name = student.getName();
        int age = student.getAge();
        int grade = student.getGrade();
        int phoneNum = student.getPhoneNum();

        TextView textView = (TextView) findViewById(R.id.tv);
        textView.setText(name + age + "" + grade + "" + phoneNum + "");

        Log.e("name","=========" + name);
        Log.e("age","=========" + age);
        Log.e("grade","=========" + grade);
        Log.e("phoneNum","=========" + phoneNum);

    }




}
