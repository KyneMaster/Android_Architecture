package com.duobang.pms_lib.session.imp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import androidx.annotation.Nullable;

public class SessionManagerImpl extends SessionManager {

    protected final SessionConfig mConfig;
    protected final Gson mGson = new Gson();
    protected SharedPreferences mSharedPreferences;

    // 用户信息获取比较频繁，作为一个字段去维护
    protected Object mToken;

    public SessionManagerImpl(SessionConfig config) {
        mConfig = config;
        Application context = config.getApplication();
        if (context == null)
            throw new NullPointerException("默认使用的SessionManger为偏好，请初始化Context");
        String configName = config.getConfigName();
        if (TextUtils.isEmpty(configName))
            configName = context.getPackageName() + ".session";
        mSharedPreferences = context.getSharedPreferences(configName, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isLogin() {
        return getToken() != null;
    }

    @Override
    public void clear() {
        super.clear();
        mToken = null; // 清除本地缓存字段
        mSharedPreferences.edit().clear().apply();
    }

    @Override
    public <T> void setToken(T token) {
        if (token == null) return;
        String json = mGson.toJson(token);
        mSharedPreferences.edit().putString("token", json).apply();
        mToken = token;
        notifyTokenChanged();
    }

    @Override
    @Nullable
    public <T> T getToken() {
        if (mConfig.getTokenClass() == null) return null;
        try {
            if (mToken != null) {
                return (T) mToken;
            }
            String json = mSharedPreferences.getString("token", null);
            if (TextUtils.isEmpty(json)) return null;
            mToken = mGson.fromJson(json, mConfig.getTokenClass());
            return (T) mToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
