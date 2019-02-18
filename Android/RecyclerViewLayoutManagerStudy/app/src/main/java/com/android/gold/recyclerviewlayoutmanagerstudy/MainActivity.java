package com.android.gold.recyclerviewlayoutmanagerstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {

    RecyclerView list;
    ArrayList<Integer> data = new ArrayList<>();
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeData();
        adapter = new ListAdapter(data);
        list = (RecyclerView) findViewById(R.id.rv);
        list.setLayoutManager(new MyLayoutManager());
        list.setAdapter(adapter);
    }


    public void makeData() {
        Observable<Integer> source = Observable.range(0, 99);
        source.subscribe( integer -> data.add(integer, integer) );
    }

}
