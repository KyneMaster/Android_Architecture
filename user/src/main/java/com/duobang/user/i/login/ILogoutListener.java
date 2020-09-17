package com.duobang.user.i.login;

public interface ILogoutListener {

    void onLogoutSuccess(String message);

    void onFailure(String message);
}
