package com.duobang.middleware;

import com.duobang.middleware.cache.PreferenceManager;
import com.duobang.middleware.router.AppRoute;
import com.duobang.pms_lib.cache.AppCacheManager;
import com.duobang.middleware.common.AppImageLoader;
import com.duobang.pms_lib.context.BaseLibApplication;
import com.duobang.pms_lib.environment.DebugEnv;
import com.duobang.pms_lib.session.CookieProvider;

public class PMSApplication extends BaseLibApplication {

    @Override
    public void initDuobangApplication() {
        super.initDuobangApplication();
        // 初始化缓存
        PreferenceManager.getInstance().initPreferences(this);
        //初始化Router
        AppRoute.init(this, DebugEnv.DEBUG);
    }

    public void clearCache() {
        // 清除图片缓存
        AppImageLoader.clearCache(getApplicationContext());
        // 清除sp缓存数据
        AppCacheManager.getInstance().clearMemoryCache();
    }

    public void logout() {
        CookieProvider.getInstance().logout();
        PreferenceManager.getInstance().logout();
        AppRoute.login(this);
    }
}
