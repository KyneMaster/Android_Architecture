package com.duobang.pms_lib.network;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.duobang.pms_lib.cache.AppCacheManager;
import com.duobang.pms_lib.core.network.DuobangResponse;
import com.duobang.pms_lib.session.CookieProvider;
import com.duobang.pms_lib.utils.DuobangLog;
import com.duobang.pms_lib.utils.JsonUtil;
import com.duobang.pms_lib.utils.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 如果有网，直接网络获取，如果无网，读取缓存
 */
public class HttpInterceptor {

    public static class HttpCacheInterceptor implements Interceptor {

        private int netCacheTime = 0;
        private Context context;
        private static final String TAG = "HttpCacheInterceptor";

        public HttpCacheInterceptor(Context context) {
            this.context = context;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response ;
            String token = CookieProvider.getInstance().getLoginCookie().getSessionId();
            if (!NetworkUtils.isNetworkAvailable(context)) {
                CacheControl tempCacheControl = new CacheControl.Builder()
                        .onlyIfCached()
                        .maxStale(HttpConstant.MAX_STALE, TimeUnit.SECONDS)
                        .build();
                request = request.newBuilder()
                        .cacheControl(tempCacheControl)
                        .header("cookie", "sessionId="+token)
                        .method(request.method(), request.body())
                        .build();
                response = chain.proceed(request);
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + HttpConstant.MAX_STALE)
                        .build();
            }else {
                request = request.newBuilder()
                        .header("cookie", "sessionId="+token)
                        .method(request.method(), request.body())
                        .build();
                response = chain.proceed(request);
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + netCacheTime)
                        .build();
            }
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            DuobangLog.i(TAG, "intercept == response: " + response.request().url() + " , data: " + responseBody.string());
            if (response.code() == 401) {
                DuobangLog.i(TAG, "intercept: 401 ——> Login");
                AppCacheManager.getInstance().logout();
                CookieProvider.getInstance().logout();
                ARouter.getInstance().build("/user/login").navigation();
                ResponseBody myBody = reviseResponseInfo();
                return response.newBuilder().body(myBody).build();
            }
            return response;
        }

        private ResponseBody reviseResponseInfo() {
            DuobangResponse errorResponse = new DuobangResponse();
            errorResponse.setCode("error");
            errorResponse.setMessage("登录失效！请重新登录！！");
            errorResponse.setData(null);
            String responseJson = JsonUtil.toJson(errorResponse);
            return ResponseBody.create(MediaType.parse("text/plain"), responseJson);
        }
    }

    /**
     * 自定义的，重试N次的拦截器
     * 通过：addInterceptor 设置
     */
    public static class Retry implements Interceptor {

        public int maxRetry;//最大重试次数
        private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

        public Retry(int maxRetry) {
            this.maxRetry = maxRetry;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            Log.i("Retry","num:"+retryNum);
            while (!response.isSuccessful() && retryNum < maxRetry) {
                retryNum++;
                Log.i("Retry","num:"+retryNum);
                response = chain.proceed(request);
            }
            return response;
        }
    }

    /**
     * 设置一个日志打印拦截器
     * 通过：addInterceptor 设置
     */
    public static class CommonLog implements Interceptor {

        //统一的日志输出控制，可以构造方法传入，统一控制日志
        private boolean logOpen = true;
        //log的日志TAG
        private String logTag = "CommonLog";

        public CommonLog() {}

        public CommonLog(boolean logOpen) {
            this.logOpen = logOpen;
        }

        public CommonLog(String logTag) {
            this.logTag = logTag;
        }

        public CommonLog(boolean logOpen, String logTag) {
            this.logOpen = logOpen;
            this.logTag = logTag;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {

            Request request = chain.request();
            long t1 = System.currentTimeMillis();//请求发起的时间
            Response response = chain.proceed(request);
            long t2 = System.currentTimeMillis();//收到响应的时间

            if (logOpen) {
                //这里不能直接使用response.body().string()的方式输出日志
                //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
                //个新的response给应用层处理
                ResponseBody responseBody = response.peekBody(1024 * 1024);
                Log.i(logTag, response.request().url() + " , use-timeMs: " + (t2 - t1) + " , data: " + responseBody.string());
            }

            return response;
        }
    }
}
