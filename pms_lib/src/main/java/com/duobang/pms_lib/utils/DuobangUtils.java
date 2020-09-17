package com.duobang.pms_lib.utils;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;

/**
 * @author Kyne
 * @// 2019/4/20
 */
public class DuobangUtils {

    /**
     * 判断正在运行的Activity
     * @param context
     * @return  boolean
     */
    public static boolean isActivityRunning(Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            return !((Activity) context).isFinishing();
        } else if (context instanceof ContextThemeWrapper) {
            Context baseContext = ((ContextThemeWrapper) context).getBaseContext();
            if (baseContext != null) {
                if (baseContext instanceof Activity) {
                    return !((Activity) baseContext).isFinishing();
                }
            }
        }
        return false;
    }

}
