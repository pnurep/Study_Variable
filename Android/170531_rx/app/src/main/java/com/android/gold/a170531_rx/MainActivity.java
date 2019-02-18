package com.android.gold.a170531_rx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import io.reactivex.Observable;



public class MainActivity extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    int dan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);

    }

    public void calc(View v) {
        dan = Integer.parseInt(editText1.getText().toString());
        Observable.range(1, 9)
                .map(row -> dan + " * " + row +" = " + (dan*row))
                .map(result -> result + "\n")
                .subscribe(editText2::append);
    }
}
