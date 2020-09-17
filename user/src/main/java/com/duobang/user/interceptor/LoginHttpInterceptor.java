package com.duobang.user.interceptor;

import android.os.Build;

import com.duobang.pms_lib.session.Cookie;
import com.duobang.pms_lib.session.CookieProvider;
import com.duobang.pms_lib.utils.DuobangLog;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 登录专用拦截器
 * TODO 主要用来拦截Cookie，并做缓存
 */
public class LoginHttpInterceptor implements Interceptor {

    private static final String TAG = "LoginHttpInterceptor";

    public LoginHttpInterceptor() {

    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().build();
        Response response = chain.proceed(request);
        response = response.newBuilder().build();
        String headers = response.headers().toString();
        if (headers.contains("Set-Cookie") || headers.contains("set-cookie")){
            Cookie cookie = getHttpCookie(response.headers().get("set-cookie"));
            DuobangLog.d(TAG, "LoginIntercept ::: cookie = " + cookie.getSessionId());
            CookieProvider.getInstance().setLoginCookie(cookie);
        }
        DuobangLog.d(TAG, "LoginIntercept ::: response: " + response.request().method());
        DuobangLog.d(TAG, "LoginIntercept ::: response: " + response.request().url());
        DuobangLog.d(TAG, "LoginIntercept ::: headers = " + headers);
        return response;
    }

    /* sessionId=ebdf25ee70e6cd74e21784447101ac13320d8199ef520764; path=/; max-age=864000; HttpOnly=true */
    private Cookie getHttpCookie(String headers) {
        List<HttpCookie> httpCookies = HttpCookie.parse(headers);
        if (httpCookies == null || httpCookies.size() == 0){
            return null;
        }
        //后台定义只有一个cookie
        HttpCookie httpCookie = httpCookies.get(0);
        Cookie cookie = new Cookie();
        cookie.setSessionId(httpCookie.getValue());
        cookie.setDomain(httpCookie.getDomain());
        cookie.setMax_age(httpCookie.getMaxAge());
        cookie.setPath(httpCookie.getPath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cookie.setHttpOnly(httpCookie.isHttpOnly());
        }
        return cookie;
    }

}