package com.duobang.middleware.common;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class CommonDialog extends Dialog {

    private static int default_width = ViewGroup.LayoutParams.WRAP_CONTENT; //默认宽度

    private static int default_height = ViewGroup.LayoutParams.WRAP_CONTENT;//默认高度

    public CommonDialog(Context context, View layout, int style) {

        this(context, default_width, default_height, layout, style);

    }

    public CommonDialog(Context context, int width, int height, View layout, int style) {

        super(context, style);

        setContentView(layout);

        Window window = getWindow();

        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();

        params.width = width;
        params.height = height;
        params.gravity = Gravity.CENTER;

        window.setAttributes(params);

    }
}
