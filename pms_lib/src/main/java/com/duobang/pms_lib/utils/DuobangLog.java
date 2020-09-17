package com.duobang.pms_lib.utils;

import android.util.Log;

import com.duobang.pms_lib.environment.DebugEnv;

public class DuobangLog {

    public static boolean DEBUG = DebugEnv.DEBUG;

    private DuobangLog() {
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Exception exception) {
        if (DEBUG) {
            Log.e(tag, exception.toString() + ":  [" + msg + "]");
            StackTraceElement[] stackTraceElements = exception.getStackTrace();

            for (StackTraceElement stackTraceElement : stackTraceElements) {
                Log.e(tag, "        at\t " + stackTraceElement.toString());
            }
        }
    }

    public static void w(String tag, String msg, Exception exception) {
        if (DEBUG) {
            Log.w(tag, exception.toString() + ":  [" + msg + "]");
            StackTraceElement[] stackTraceElements = exception.getStackTrace();

            for (StackTraceElement stackTraceElement : stackTraceElements) {
                Log.e(tag, "        at\t " + stackTraceElement.toString());
            }
        }
    }

    public static void wtf(String tag, String msg, Exception exception) {
        if (DEBUG) {
            Log.wtf(tag, exception.toString() + ":  [" + msg + "]");
            StackTraceElement[] stackTraceElements = exception.getStackTrace();

            for (StackTraceElement stackTraceElement : stackTraceElements) {
                Log.e(tag, "        at\t " + stackTraceElement.toString());
            }
        }
    }
}
