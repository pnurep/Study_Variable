package com.example.gold.memowithnodejs;

import android.database.Observable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.gold.memowithnodejs.domain.Data;
import com.example.gold.memowithnodejs.domain.Qna;
import com.google.gson.Gson;

public class WriteActivity extends AppCompatActivity {

    EditText editTitle, editAuthor, editContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editAuthor = (EditText) findViewById(R.id.editAuthor);
        editContent = (EditText) findViewById(R.id.editContent);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력 진행 상태 결과
                AsyncTask<String, Void, String> networkTask = new AsyncTask<String, Void, String>(){

                    @Override
                    protected String doInBackground(String... params) {
                        String title = params[0];
                        String name = params[1];
                        String content = params[2];

                        Qna qna = new Qna();
                        qna.setTitle(title);
                        qna.setName(name);
                        qna.setContent(content);

                        Gson gson = new Gson();
                        String jsonString =  gson.toJson(qna);

                        String result = Remote.postJson(MainActivity.SITE_URL + "bbs", jsonString);

                        return result;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        Snackbar.make(view, result, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                };
                networkTask.execute(editTitle.getText().toString(),
                        editAuthor.getText().toString(), editContent.getText().toString());
            }
        });
    }
}
