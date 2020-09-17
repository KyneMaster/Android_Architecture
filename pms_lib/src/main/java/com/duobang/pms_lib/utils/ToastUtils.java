package com.duobang.pms_lib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Kyne
 * @// 2019/4/20 Toast工具类
 */
public class ToastUtils {

    private static Toast toast;
    private static Context context;

    public static void init(Context context) {
        ToastUtils.context = context;
        toast = new Toast(context);
    }

    private static void showToast(String message, int length) {
        toast.cancel();
        toast = Toast.makeText(context, message, length);
        toast.show();

    }

    public static void longToast(String toast) {
        showToast(toast, Toast.LENGTH_LONG);
    }

    public static void longToast(int id) {
        longToast(context.getString(id));
    }

    public static void shortToast(String toast) {
        showToast(toast, Toast.LENGTH_SHORT);
    }

    public static void shortToast(int id) {
        shortToast(context.getString(id));
    }

    public static void customToast(String toast, int length) {
        showToast(toast, length);
    }

    public static void customToast(int id, int length) {
        customToast(context.getString(id), length);
    }

}
