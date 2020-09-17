package com.duobang.user.i.org;

import com.duobang.user.core.login.User;

import java.util.List;

public interface IGroupUserListener {

    void onGroupUserList(List<User> users);

    void onFailure(String message);
}
