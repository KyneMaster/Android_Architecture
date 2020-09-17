package com.duobang.user.i.login;

import com.duobang.pms_lib.core.network.DuobangResponse;

import io.reactivex.Observable;
import retrofit2.http.DELETE;

public interface ILogoutNetApi {

    /**
     * 退出登录
     */
    @DELETE("api/account/v1/sign/signout")
    Observable<DuobangResponse<Object>> loginOut();
}
