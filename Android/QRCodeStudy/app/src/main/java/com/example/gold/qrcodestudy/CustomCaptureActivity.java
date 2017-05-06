package com.example.gold.qrcodestudy;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.List;

public class CustomCaptureActivity extends CaptureActivity{

    //회전을위한 커스텀 액티비티

    private DialogInterface.OnClickListener dialogCancelClick;

    BarcodeView barcodeView;
    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                handleDecode(result);
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialogCancelClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                barcodeView.resume();//Resume scanning
                dialog.dismiss();
            }
        };

    }


    public void handleDecode(BarcodeResult rawResult) {
        barcodeView.pause();//Pause preview
        String result = rawResult.getText();

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("스캔결과")
                .setMessage("")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
