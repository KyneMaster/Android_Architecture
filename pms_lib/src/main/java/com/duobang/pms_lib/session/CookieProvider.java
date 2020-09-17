package com.duobang.pms_lib.session;

import android.app.Application;

import com.duobang.pms_lib.session.imp.SessionManager;

import io.reactivex.annotations.Nullable;

/**
 * Cookie提供者，保存登录信息，对已登录的用户进行操作。
 * <pre>
 * 使用前请先调用{@link #init(Application)}}方法进行初始化
 * </pre>
 */
public class CookieProvider {

    private static CookieProvider cookieProvider;
    private SessionManager mSessionManager;

    public static CookieProvider getInstance() {
        if (cookieProvider == null) {
            throw new NullPointerException("Cookie提供程序没有初始化！");
        }
        return cookieProvider;
    }

    public static void init(Application applicationContext) {
        if (cookieProvider == null) {

            SessionManager.init(new SessionManager
                    .Builder()
                    .tokenClass(Cookie.class)
                    .withContext(applicationContext)
                    .build());

            cookieProvider = new CookieProvider();
        }
    }

    private CookieProvider() {
        mSessionManager = SessionManager.getDefault();
    }

    /**
     * 保存当前Cookie，一般在登录的时候调用该方法
     *
     * @param cookie CookieObj
     */
    public void setLoginCookie(Cookie cookie) {
        mSessionManager.setToken(cookie);
    }

    /**
     * 获取当前登录Cookie类
     *
     * @return CookieObj
     */
    @Nullable
    public Cookie getLoginCookie() {
        return mSessionManager.getToken();
    }

    /**
     * 是否登录
     */
    public boolean isLogin() {
        return mSessionManager.isLogin();
    }

    public boolean isNotLogin() {
        return !isLogin();
    }

    /**
     * 退出登录，释放资源
     */
    public void logout() {
        mSessionManager.clear();
    }
}
