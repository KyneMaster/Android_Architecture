package com.duobang.pms_lib.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class IMEUtils {

    public static void toggleIME(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showIME(final Context context, final View view) {
        view.postDelayed(new Runnable() {

            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }, 200);

    }

    public static void showIMEAutoClose(final View view) {
        view.postDelayed(new Runnable() {

            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                view.setFocusable(true);
                view.requestFocus();
                view.setFocusableInTouchMode(true);
                imm.showSoftInput(view, 0);
            }
        }, 0);

    }

    public static void hideIME(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 200);
    }

    public static boolean isIMEAlive(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

}
