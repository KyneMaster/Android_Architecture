package com.duobang.middleware.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.duobang.pms_lib.session.CookieProvider;

/**
 * 登录路由拦截器
 */
@Interceptor(priority = 1)
public class LoginRouteInterceptor implements IInterceptor {

    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();

        // 授权登录的路由，没有登录自动跳转到登录界面
        if (!path.contains("user") && CookieProvider.getInstance().isNotLogin()) {
            AppRoute.login(mContext);
            callback.onInterrupt(new RuntimeException("拦截到登录失效，需重新登录"));
            return;
        }
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
