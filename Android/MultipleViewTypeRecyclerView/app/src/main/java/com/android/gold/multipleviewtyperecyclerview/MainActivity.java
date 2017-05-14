package com.android.gold.multipleviewtyperecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Object> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mData = new ArrayList<>();
        mData.add(new User("홍길동", "서울특별시 강서구"));
        mData.add("테스트 1");
        mData.add("테스트 2");
        mData.add(R.drawable.img1);
        mData.add(new User("고길동", "불쌍한 아저씨"));
        mData.add(new User("미츠하", "일본"));
        mData.add(R.drawable.img2);
        mData.add(R.drawable.img3);
        mData.add("테스트 3");
        mData.add(R.drawable.img4);
        mData.add("테스트 4");
        mData.add(R.drawable.img5);
        mData.add("테스트 5");
        mData.add(new User("타키", "너의 이름은"));

        CustomAdapter adapter = new CustomAdapter(this, mData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
