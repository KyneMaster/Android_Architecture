package com.duobang.user.i.login;

import com.duobang.user.core.login.User;

public interface IRegisterListener {

    void onRegisterSuccess(User user);

    void onFailure(String message);
}
