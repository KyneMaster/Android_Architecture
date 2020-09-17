package com.duobang.pms_lib.network;

import android.content.Context;

import com.duobang.pms_lib.environment.DebugEnv;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreator {

    private static Retrofit retrofit;

    // 注意: BASE_URL都是统一的AppConfig.BASE_URL(有需要的话可以考虑使用简单工厂，根据参数来创建retrofit)
    public static Retrofit getRetrofit(Context applicationContext) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = HttpClient.getInstance(applicationContext);
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(DebugEnv.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
