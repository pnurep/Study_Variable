package com.kitkat.android.iumusic.Util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

public class Permission {

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermission(Activity activity, String[] permissionArr, int requestCode) {
        boolean flag = true;

        for(String permission : permissionArr) {
            if(activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(permissionArr, requestCode);
                flag = false;
                break;
            }
        }

        return flag;
    }

    public static boolean onCheckResult(int[] grantResults) {
        boolean flag = true;

        for(int grantResult : grantResults) {
            if(grantResult != PackageManager.PERMISSION_GRANTED) {
                flag = false;
                break;
            }
        }

        return flag;
    }
}
