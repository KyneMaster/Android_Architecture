package com.duobang.user.login.contract;

public interface ISSCodePresenter {

    /**
     * 手机号快捷登录
     */
    void login();

    /**
     * 激活账号
     */
    void activateAccount();

    /**
     * 重发验证码
     */
    void resendSSCode();
}
