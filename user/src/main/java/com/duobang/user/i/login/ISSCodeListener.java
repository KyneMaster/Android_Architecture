package com.duobang.user.i.login;

public interface ISSCodeListener {

    void loadSSCode(String message);

    void onFailure(String message);
}
