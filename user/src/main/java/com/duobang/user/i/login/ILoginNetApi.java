package com.duobang.user.i.login;

import com.duobang.pms_lib.core.network.DuobangResponse;
import com.duobang.user.core.login.Account;
import com.duobang.user.core.login.User;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ILoginNetApi {

    /**
     * 账号登录
     * @param body AccountLogin -> username/password
     * @return Response
     */
    @POST("api/account/v1/sign/signin/username?from=Android")
    Observable<DuobangResponse<Account>> accountLogin(@Body RequestBody body);

    /**
     * 手机号登录
     * @param body PhoneLogin -> phone/captcha
     * @return Response
     */
    @POST("api/account/v1/sign/signin/phone?from=Android")
    Observable<DuobangResponse<Account>> phoneLogin(@Body RequestBody body);

    /**
     * 获取验证码
     * @param phone 手机号
     * @return Response
     */
    @GET("api/account/v1/sign/captcha/phone/{phone}")
    Observable<Response<ResponseBody>> loadSSCode(@Path("phone") String phone);

    /**
     * 注册
     * @param body Register -> username/password/phone/nickname
     * @return Response
     */
    @POST("api/account/v1/sign/signup?from=Android")
    Observable<DuobangResponse<User>> register(@Body RequestBody body);

    /**
     * 激活用户
     * @param body PhoneLogin -> phone/captcha
     * @return Response
     */
    @POST("api/account/v1/sign/captcha?from=Android")
    Observable<DuobangResponse> activateAccount(@Body RequestBody body);

    /**
     * 退出登录
     */
    @DELETE("api/account/v1/sign/signout")
    Observable<Response<ResponseBody>> loginOut();

    /**
     * 验证账号是否存在
     * @param username 用户名（账号）
     * @return Response {exist:boolean}
     */
    @GET("api/account/v1/sign/username/{username}/exit")
    Observable<Response<ResponseBody>> verifyAccount(@Path("username") String username);

}
