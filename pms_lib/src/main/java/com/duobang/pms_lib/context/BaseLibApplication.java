package com.duobang.pms_lib.context;

import android.app.Application;
import android.content.Context;

import com.duobang.pms_lib.common.DuobangCrashHandler;
import com.duobang.pms_lib.environment.DebugEnv;
import com.duobang.pms_lib.sentry.SentryUtils;
import com.duobang.pms_lib.session.CookieProvider;
import com.duobang.pms_lib.utils.MessageUtils;

import androidx.multidex.MultiDex;

public class BaseLibApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationContext.getInstance().init(this);
        initDuobangApplication();
    }

    public void initDuobangApplication() {
        //初始化Toast
        MessageUtils.init(this);
        //初始化Sentry
        SentryUtils.init(this, DebugEnv.sentryDsn);
        //初始化cookie提供者
        CookieProvider.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        DuobangCrashHandler.getInstance().register(this);
        MultiDex.install(this);
    }

}
