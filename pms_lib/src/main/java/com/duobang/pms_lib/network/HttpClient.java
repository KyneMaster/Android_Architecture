package com.duobang.pms_lib.network;

import android.content.Context;

import com.duobang.pms_lib.environment.DebugEnv;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class HttpClient {

    public static OkHttpClient getInstance(Context applicationContext){
        Cache cache = new Cache(applicationContext.getCacheDir(), HttpConstant.HTTP_CACHE_SIZE);
        OkHttpClient okHttpClient;
        if (DebugEnv.DEBUG) {
            okHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(HttpConstant.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(HttpConstant.READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(HttpConstant.WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addNetworkInterceptor(new HttpInterceptor.HttpCacheInterceptor(applicationContext))
                    .addInterceptor(new HttpInterceptor.HttpCacheInterceptor(applicationContext))
                    .build();
        } else {
            okHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .cache(cache)
                    .connectTimeout(HttpConstant.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(HttpConstant.READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(HttpConstant.WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addNetworkInterceptor(new HttpInterceptor.HttpCacheInterceptor(applicationContext))
                    .addInterceptor(new HttpInterceptor.HttpCacheInterceptor(applicationContext))
                    .build();
        }
        return okHttpClient;
    }
}
