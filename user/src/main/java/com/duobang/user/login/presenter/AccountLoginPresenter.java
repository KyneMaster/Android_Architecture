package com.duobang.user.login.presenter;

import com.duobang.middleware.cache.PreferenceManager;
import com.duobang.pms_lib.framework.BasePresenter;
import com.duobang.pms_lib.utils.LoadingUtils;
import com.duobang.pms_lib.utils.MessageUtils;
import com.duobang.user.core.login.Account;
import com.duobang.user.core.login.User;
import com.duobang.user.core.login.imp.LoginNetWork;
import com.duobang.user.core.user.imp.UserNetWork;
import com.duobang.user.i.login.ILoginListener;
import com.duobang.user.i.login.ISSCodeListener;
import com.duobang.user.i.user.IUserInfoListener;
import com.duobang.user.login.contract.IAccountLoginPresenter;
import com.duobang.user.login.contract.IAccountLoginView;

public class AccountLoginPresenter extends BasePresenter<IAccountLoginView> implements IAccountLoginPresenter {

    @Override
    protected void onStart() {
        checkViewAttached();
        login();
    }

    @Override
    public void login() {
        LoadingUtils.showLoadingDialog(getView().getContext());
        LoginNetWork.getInstance().accountLogin(getView().getAccount(), getView().getPassword(), new ILoginListener() {

            @Override
            public void onLoginSuccess(String message) {
                loadUserInfo();
            }

            @Override
            public void onNotAvailable(Account account) {
                activateAccount(account.getPhone());
            }

            @Override
            public void onFailure(String message) {
                LoadingUtils.dismissLoadingDialog();
                MessageUtils.shortToast(message);
            }
        });
    }

    private void loadUserInfo() {
        UserNetWork.getInstance().loadPersonInfo(new IUserInfoListener() {
            @Override
            public void onUserInfo(User user) {
                saveUserInfo(user);
                LoadingUtils.dismissLoadingDialog();
                getView().startMainView();
            }

            @Override
            public void onFailure(String message) {
                LoadingUtils.dismissLoadingDialog();
                MessageUtils.shortToast(message);
            }
        });
    }

    private void saveUserInfo(User user) {
        PreferenceManager.getInstance().getUserPreferences().saveUserId(user.getId());
        PreferenceManager.getInstance().getUserPreferences().saveUserName(user.getUsername());
        PreferenceManager.getInstance().getUserPreferences().saveUserAvatar(user.getAvatar());
        PreferenceManager.getInstance().getUserPreferences().saveUserPhone(user.getPhone());
        PreferenceManager.getInstance().getUserPreferences().saveUserState(user.getState());
        PreferenceManager.getInstance().getUserPreferences().saveNickName(user.getNickname());
    }

    /**
     * 激活账号
     * 先发送验证码，然后跳转验证码页面
     *
     * @param phone 手机号
     */
    private void activateAccount(final String phone) {
        LoginNetWork.getInstance().sendSSCode(phone, new ISSCodeListener() {

            @Override
            public void loadSSCode(String message) {
                LoadingUtils.dismissLoadingDialog();
                getView().startActivateView(phone);
            }

            @Override
            public void onFailure(String message) {
                LoadingUtils.dismissLoadingDialog();
                MessageUtils.shortToast(message);
            }
        });
    }

}
