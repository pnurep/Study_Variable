package com.kitkat.android.iumusic.Util;

public class Util {

    public static String millToTime(long mili){
        long m = mili / 1000 / 60;
        long s = mili / 1000 % 60;

        return String.format("%02d", m) + ":" + String.format("%02d", s);
    }

}
