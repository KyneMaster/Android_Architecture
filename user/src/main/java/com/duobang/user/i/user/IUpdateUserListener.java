package com.duobang.user.i.user;

import com.duobang.user.core.login.User;

public interface IUpdateUserListener {

    void onUpdatedSuccess(User user);

    void onFailure(String message);
}
