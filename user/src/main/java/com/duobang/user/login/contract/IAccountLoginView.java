package com.duobang.user.login.contract;

import com.duobang.pms_lib.i.framework.IBaseView;

public interface IAccountLoginView extends IBaseView {

    String getAccount();

    String getPassword();

    /**
     * 跳转账号激活页面 - SSCodeActivity
     * @param phone 手机号
     */
    void startActivateView(String phone);

    /**
     * 开启主页面
     */
    void startMainView();
}
