package com.example.gold.smssendobserverexample;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SmsSendObserver.SmsSendListener {

    Button button;
    EditText et_pNum, et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        et_pNum = (EditText) findViewById(R.id.pNum);
        et_content = (EditText) findViewById(R.id.content);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        sendMessage(et_pNum.getText().toString(), et_content.getText().toString());
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                    }
                };

                TedPermission.with(MainActivity.this).setPermissionListener(permissionlistener).
                        setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]").
                        setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION).check();

            }
        });
    }

    @Override
    public void onSmsSendEvent(boolean sent) {
        if (sent)
            Toast.makeText(this, sent ? "Message was sent" : "Timed out", Toast.LENGTH_SHORT).show();
    }

    public void sendMessage(String phoneNumber, String messageBody) {
        // This example has a timeout set to 15 seconds
        new SmsSendObserver(this, phoneNumber, -1).start();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("address", phoneNumber);
        intent.putExtra("sms_body", messageBody);
        intent.putExtra("exit_on_sent", true);
        startActivity(intent);
    }
}
