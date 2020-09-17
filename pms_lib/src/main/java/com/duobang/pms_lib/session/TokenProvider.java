package com.duobang.pms_lib.session;

import android.app.Application;

import com.duobang.pms_lib.session.imp.SessionManager;

import io.reactivex.annotations.Nullable;

/**
 * Token提供者，保存TOKEN信息，对已登录的用户进行操作。
 * <pre>
 * 使用前请先调用{@link #init(Application)}}方法进行初始化
 * </pre>
 */
public final class TokenProvider {

    private static TokenProvider sTokenProvider;
    private SessionManager mSessionManager;

    public static TokenProvider getInstance() {
        if (sTokenProvider == null) {
            throw new NullPointerException("TOKEN提供程序没有初始化！");
        }
        return sTokenProvider;
    }

    public static void init(Application applicationContext) {
        if (sTokenProvider == null) {

            SessionManager.init(new SessionManager
                    .Builder()
                    .withContext(applicationContext)
                    .build());

            sTokenProvider = new TokenProvider();
        }
    }

    private TokenProvider() {
        mSessionManager = SessionManager.getDefault();
    }

    /**
     * 保存当前Token，一般在登录的时候调用该方法
     *
     * @param token tokenObj
     */
    public void setLoginToken(Token token) {
        mSessionManager.setToken(token);
    }

    /**
     * 获取当前登录Token类
     *
     * @return TokenObj
     */
    @Nullable
    public Token getLoginToken() {
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
