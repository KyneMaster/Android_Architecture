package com.duobang.user.i.login;

public interface IActivateAccountListener {

    void onActivateAccount(String message);

    void onFailure(String message);
}
