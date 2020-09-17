package com.android.architecture.i.update;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUpdateNetApi {

    @GET("apps/latest/{appId}")
    Observable<Response<ResponseBody>> getAppInfo(@Path("appId") String appId, @Query("api_token") String api_token);
}
