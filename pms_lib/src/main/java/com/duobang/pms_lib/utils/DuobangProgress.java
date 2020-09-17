package com.duobang.pms_lib.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * @deprecated 系统原始Progress, 弃用
 */
public class DuobangProgress {
    static ProgressDialog show;
    private final String TAG = "MoerProgress";

    public static boolean isShowing() {
        return show != null && show.isShowing();
    }

    /**
     * 默认不可取消的进度提
     */
    public static void show(Context context, String text) {
        show(context, text, false);
    }

    public static void show(Context context, int resId) {
        show(context, context.getString(resId), false);
    }

    /**
     * 可设置是否可取消的进度提
     */
    public static void show(Context context, String text, boolean cancelAble) {
        show(context, null, text, true, cancelAble);
    }

    public static void show(Context context, int resId, boolean cancelAble) {
        show(context, null, context.getString(resId), true, cancelAble);
    }

    /**
     * 对系统默认进度提示的包装
     */
    public static void show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        if (isShowing()) {
            if (show.getContext().equals(context)) {
                show.setTitle(title);
                show.setCancelable(cancelable);
                show.setIndeterminate(indeterminate);
                show.setMessage(message);
            } else {
                dismiss(context);
                show = ProgressDialog.show(context, title, message, indeterminate, cancelable);
            }
        } else {
            show = ProgressDialog.show(context, title, message, indeterminate, cancelable);
        }
    }

    /**
     * 更新进度
     */
    public static void update(String text) {
        if (show != null) {
            show.setMessage(text);
        }
    }

    public static void dismiss(Context context) {
        if (context != null && context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (isShowing()) {
            try {
                show.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            show = null;
        }
    }
}
