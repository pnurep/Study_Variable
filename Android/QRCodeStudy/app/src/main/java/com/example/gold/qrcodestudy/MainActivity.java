package com.example.gold.qrcodestudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView txtName, txtUrl;
    Button btnScan;

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtName = (TextView) findViewById(R.id.txtName);
        txtUrl = (TextView) findViewById(R.id.txtURL);
        btnScan = (Button) findViewById(R.id.btnScan);

        qrScan = new IntentIntegrator(this);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.setPrompt("Scanning...");
                qrScan.setCaptureActivity(CustomCaptureActivity.class);
                qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(MainActivity.this, "취소!", Toast.LENGTH_SHORT).show();
            }else if((result.getContents().startsWith("http://"))){
                Toast.makeText(MainActivity.this, "스캔완료", Toast.LENGTH_SHORT).show();
                Log.e("스캔결과","==============" + result.getContents());

                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("url", result.getContents());
                startActivity(intent);

//                try {
//                    JSONObject jsonObject = new JSONObject(result.getContents());
//                    txtName.setText(jsonObject.getString("name"));
//                    txtUrl.setText(jsonObject.getString("address"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }else {
                Toast.makeText(MainActivity.this, "QR코드가 아닙니다!", Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
