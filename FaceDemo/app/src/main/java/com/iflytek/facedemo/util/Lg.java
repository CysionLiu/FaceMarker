package com.iflytek.facedemo.util;

import android.util.Log;

/**
 * Created by xianshang.liu on 2017/7/10.
 */

public class Lg {

    private static boolean isDebug = true;

    public static void trace(String msg) {
        if (isDebug) {
            Log.d("flag--", msg);
        }
    }

    public static void setIsDebug(boolean aIsDebug) {
        isDebug = aIsDebug;
    }
}
