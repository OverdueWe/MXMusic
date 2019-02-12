package com.mxnavi.gf.misc.util;

import android.util.Log;

/**
 * describle : Log封装
 *
 * @author Mark
 * @date 2018.12.03
 */

public class MxLog {

    private static final String TAG = "MxLog";

    private static boolean debug = true;

    private MxLog() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        MxLog.debug = debug;
    }

    public static void d(String tag, String message) {
        if (debug) {
            Log.d(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (debug) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (debug) {
            Log.e(tag, message);
        }
    }
}
