package com.duobang.user.i.user;

import com.duobang.pms_lib.core.network.DuobangResponse;
import com.duobang.user.core.login.User;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserNetApi {

    /**
     * 获取自己信息
     *
     * @return DuobangResponse<User>
     */
    @GET("api/account/v1/user/my/info")
    Observable<DuobangResponse<User>> loadPersonInfo();

    /**
     * 获取用户信息
     * @param id 用户ID
     * @return DuobangResponse<User>
     */
    @GET("api/account/v1/user/{id}")
    Observable<DuobangResponse<User>> loadUserInfo(@Path("id") String id);

    /**
     * 上传头像文件到OSS服务器，获取返回的URL
     *
     * @param body
     * @return
     */
    @POST("api/file/image")
    Observable<DuobangResponse<String>> uploadAvatarFile(@Body RequestBody body);

    /**
     * 更新头像
     *
     * @param body
     * @return
     */
    @PUT("api/account/v1/user/avatar")
    Observable<DuobangResponse<User>> updateUserAvatar(@Body RequestBody body);

    /**
     * 更新密码
     * @param body
     * @return
     */
    @PUT("api/account/v1/user/password")
    Observable<DuobangResponse<User>> updateUserPassword(@Body RequestBody body);

    /**
     * 更新用户名
     *
     * @param body
     * @return
     */
    @PUT("api/account/v1/user/username")
    Observable<DuobangResponse<User>> updateUserName(@Body RequestBody body);

    /**
     * 更新昵称
     *
     * @param body
     * @return
     */
    @PUT("api/account/v1/user/nickname")
    Observable<DuobangResponse<User>> updateNickName(@Body RequestBody body);

    /**
     * 使用验证码修改密码
     *
     * @param body
     * @return
     */
    @PUT("api/account/v1/user/password/captcha")
    Observable<DuobangResponse<User>> updatePasswordByPhone(@Body RequestBody body);

}
