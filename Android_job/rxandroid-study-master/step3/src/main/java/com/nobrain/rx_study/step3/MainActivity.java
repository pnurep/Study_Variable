package com.nobrain.rx_study.step3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.nobrain.rx_study.step1.R;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        View v1 = findViewById(R.id.iv_1);
        View v2 = findViewById(R.id.iv_2);

        v1.setOnClickListener(view -> {

            Completable.fromCallable(() -> {
                // do some server
                return true;
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Toast.makeText(this, "iv1 Success", Toast.LENGTH_SHORT).show();
                    });
        });

        v2.setOnClickListener(view -> {
            Completable.fromCallable(() -> {
                throw new Exception("Invalid!!");
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Toast.makeText(this, "Iv2 Success, It cannot be.", Toast.LENGTH_SHORT).show();
                    }, t -> {
                        Toast.makeText(this, "vi2 must be Failed!!", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
