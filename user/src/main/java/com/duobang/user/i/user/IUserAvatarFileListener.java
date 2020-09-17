package com.duobang.user.i.user;

public interface IUserAvatarFileListener {

    void onAvatarUrl(String url);

    void onFailure(String message);
}
