package com.duobang.middleware.common;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.duobang.middleware.weight.AvatarView;
import com.duobang.pms_lib.utils.DuobangUtils;

import androidx.annotation.Nullable;

/**
 * APP 图片加载器
 */
public final class AppImageLoader {

    /**
     * 替代图资源ID
     */
    private static int DEFAULT_PLACE_HOLDER_ID = -1;

    /**
     * 错误替代图资源ID
     */
    private static int DEFAULT_PLACE_HOLDER_ERROR_ID = -1;

    /**
     * 头像替代图资源ID
     */
    private static int DEFAULT_AVATAR_PLACE_HOLDER_ID = -1;


    private static void checkPlaceHolder(Context context) {
        if (DEFAULT_PLACE_HOLDER_ID == -1) {
            DEFAULT_PLACE_HOLDER_ID = context.getResources().getIdentifier("default_placeholder_normal", "drawable", context.getPackageName());
        }
        if (DEFAULT_PLACE_HOLDER_ERROR_ID == -1) {
            DEFAULT_PLACE_HOLDER_ERROR_ID = context.getResources().getIdentifier("default_placeholder_error", "drawable", context.getPackageName());
        }
        if (DEFAULT_AVATAR_PLACE_HOLDER_ID == -1) {
            DEFAULT_AVATAR_PLACE_HOLDER_ID = context.getResources().getIdentifier("default_avatar_placeholder", "drawable", context.getPackageName());
        }
    }

    /**
     * 有默认头像
     */
    public static void displayPlaceholderAvatar(@Nullable String url, @Nullable ImageView view) {
        assert view != null;
        checkPlaceHolder(view.getContext());
        displayAvatar(url, view, DEFAULT_AVATAR_PLACE_HOLDER_ID);
    }

    private static void displayAvatar(@Nullable String url, @Nullable ImageView view, int resId) {
        if (view == null)
            return;
        if (DuobangUtils.isActivityRunning(view.getContext())) {
            Glide.with(view)
                    .load(url)
                    .placeholder(resId)
                    .error(resId)
                    .centerCrop()
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade(600))
                    .into(view);
        }
    }

    /**
     * 加载头像,
     *
     * @param url 路径
     * @param view ImageView
     */
    public static void displayAvatar(@Nullable String url, @Nullable ImageView view) {
        if (view == null)
            return;
        if (DuobangUtils.isActivityRunning(view.getContext())) {
            Glide.with(view)
                    .load(url)
                    .centerCrop()
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade(600))
                    .into(view);
        }
    }

    /**
     * 加载头像,
     *
     * @link 用于AvatarView布局，姓名头像
     * @param url 路径
     * @param userName 用户名
     * @param view ImageView
     */
    public static void displayAvatar(@Nullable String url, @Nullable String userName, @Nullable AvatarView view) {
        if (view == null)
            return;
        view.setUserName("");
        if (url == null || url.equals("")) {
            view.setUserName(userName);
        }
        if (DuobangUtils.isActivityRunning(view.getContext())) {
            Glide.with(view)
                    .load(url)
                    .centerCrop()
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade(600))
                    .into(view);
        }
    }

    /**
     * 加载图片
     */
    public static void display(@Nullable String url, @Nullable ImageView view) {
        if (view == null)
            return;
        if (url == null || url.length() == 0){
            checkPlaceHolder(view.getContext());
            if (DuobangUtils.isActivityRunning(view.getContext())) {
                Glide.with(view)
                        .load(url)
                        .placeholder(DEFAULT_AVATAR_PLACE_HOLDER_ID)
                        .error(DEFAULT_AVATAR_PLACE_HOLDER_ID)
                        .into(view);
            }
        } else {
            if (DuobangUtils.isActivityRunning(view.getContext())) {
                Glide.with(view)
                        .load(url)
                        .centerCrop()
                        .into(view);
            }
        }
    }

    /**
     * 没有默认的替代图加载图片方式
     */
    public static void displayWithoutPlaceHolder(@Nullable String url, @Nullable ImageView view) {
        if (TextUtils.isEmpty(url) || view == null) {
            return;
        }
        if (DuobangUtils.isActivityRunning(view.getContext())) {
            Glide.with(view)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade(300))
                    .centerCrop()
                    .into(view);
        }
    }

    /**
     * 清除缓存
     */
    public static void clearCache(final Context applicationContext) {
        clearMemoryCache(applicationContext); // 在主线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 在线程中
                Glide.get(applicationContext).clearDiskCache();
            }
        }).start();
    }

    /**
     * 清除缓存
     */
    public static void clearMemoryCache(Context applicationContext) {
        Glide.get(applicationContext).clearMemory();
    }
}
