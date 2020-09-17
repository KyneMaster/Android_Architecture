package com.duobang.middleware.common;

import android.app.Activity;

import com.duobang.middleware.R;
import com.duobang.middleware.environment.AppConfig;
import com.duobang.pms_lib.core.glide.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

/**
 * APP matisse图片选择器
 */
public class AppPictureSelector {

    /**
     * 打开选择器
     *
     * @param activity 当前Activity
     * @param count 图片最大选择数量
     * @param RESULT_CODE 返回码 ——> onActivityResult()
     */
    public static void takePhotoSelector(Activity activity, int count, int RESULT_CODE) {
        if (activity == null || count == 0){
            return;
        }
        Matisse.from(activity)
                .choose(MimeType.ofImage())
                .showSingleMediaType(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, AppConfig.PROVIDER_AUTHORITY))
                .countable(true)
                .maxSelectable(count)
                .thumbnailScale(0.8f)
                .theme(R.style.MyMatisse)
                .imageEngine(new Glide4Engine())
                .originalEnable(true)
                .forResult(RESULT_CODE);
    }

}
