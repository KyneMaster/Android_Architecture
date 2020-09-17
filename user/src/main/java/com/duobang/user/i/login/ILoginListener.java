package com.duobang.user.i.login;

import com.duobang.user.core.login.Account;

public interface ILoginListener {

    void onLoginSuccess(String message);

    void onNotAvailable(Account account);

    void onFailure(String message);
}
