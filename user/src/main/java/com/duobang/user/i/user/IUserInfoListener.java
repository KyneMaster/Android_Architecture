package com.duobang.user.i.user;

import com.duobang.user.core.login.User;

public interface IUserInfoListener {

    void onUserInfo(User user);

    void onFailure(String message);
}
