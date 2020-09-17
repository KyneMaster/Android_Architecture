package com.duobang.pms_lib.session.imp;

import android.app.Application;

import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;

/**
 * Session配置信息
 */
public class SessionConfig {

    private Class<?> tokenClass;
    private WeakReference<Application> mApplication;
    private String configName;

    public Class<?> getTokenClass() {
        return tokenClass;
    }

    public void setTokenClass(Class<?> userTokenClass) {
        this.tokenClass = userTokenClass;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    @Nullable
    public Application getApplication() {
        if (mApplication == null) return null;
        return mApplication.get();
    }

    public void setApplication(Application application) {
        mApplication = new WeakReference<>(application);
    }
}
