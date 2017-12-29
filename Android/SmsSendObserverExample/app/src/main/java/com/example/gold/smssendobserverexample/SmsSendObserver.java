package com.example.gold.smssendobserverexample;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;

/**
 * Created by Gold on 2017-12-29.
 */

public class SmsSendObserver extends ContentObserver {

    public static final Handler handler = new Handler();
    public static final int NO_TIMEOUT = -1;
    private static final Uri uri = Uri.parse("content://sms/");

    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_TYPE = "type";
    private static final String[] PROJECTION = { COLUMN_ADDRESS, COLUMN_TYPE };
    private static final int MESSAGE_TYPE_SENT = 2;

    private boolean wasSent = false;
    private boolean timedOut = false;

    Context context;
    String pNum;
    long timeOut = NO_TIMEOUT;

    private ContentResolver resolver;

    public interface SmsSendListener {
        void onSmsSendEvent(boolean sent);
    }

    public SmsSendObserver(Context context, String pNum, long timeOut) {
        super(handler);

        if (context instanceof SmsSendListener) {
            this.context = context;
            resolver = context.getContentResolver();
            this.pNum = pNum;
            this.timeOut = timeOut;
        } else {
            throw new IllegalStateException("Context must implement SmsSendListener interface");
        }
    }

    private Runnable runOut = new Runnable() {
        @Override
        public void run() {
            if (!wasSent) {
                timedOut = true;
                callBack();
            }
        }
    };

    private void callBack() {
        ((SmsSendListener) context).onSmsSendEvent(wasSent);
        stop();
    }

    public void start() {
        if (resolver != null) {
            resolver.registerContentObserver(uri, true, this);

            if (timeOut > NO_TIMEOUT) {
                handler.postDelayed(runOut, timeOut);
            }
        }
        else {
            throw new IllegalStateException(
                    "Current SmsSendObserver instance is invalid");
        }
    }

    public void stop() {
        if(resolver != null) {
            resolver.unregisterContentObserver(this);

            resolver = null;
            context = null;
        }
    }

    @Override
    public void onChange(boolean selfChange) {
        if (wasSent || timedOut)
            return;

        Cursor cursor = null;

        try {
            cursor = resolver.query(uri, PROJECTION, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                final String address =
                        cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
                final int type =
                        cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE));

                if (PhoneNumberUtils.compare(address, pNum) &&
                        type == MESSAGE_TYPE_SENT) {

                    wasSent = true;
                    callBack();
                }
            }
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
