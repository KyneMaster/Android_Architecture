package com.duobang.user.login.presenter;

import com.duobang.middleware.cache.PreferenceManager;
import com.duobang.pms_lib.framework.BasePresenter;
import com.duobang.pms_lib.utils.LoadingUtils;
import com.duobang.pms_lib.utils.MessageUtils;
import com.duobang.user.core.login.Account;
import com.duobang.user.core.login.User;
import com.duobang.user.core.login.imp.LoginNetWork;
import com.duobang.user.core.user.imp.UserNetWork;
import com.duobang.user.i.login.IActivateAccountListener;
import com.duobang.user.i.login.ILoginListener;
import com.duobang.user.i.login.ISSCodeListener;
import com.duobang.user.i.user.IUserInfoListener;
import com.duobang.user.login.contract.ISSCodePresenter;
import com.duobang.user.login.contract.ISSCodeView;

public class SSCodePresenter extends BasePresenter<ISSCodeView> implements ISSCodePresenter {

    @Override
    protected void onStart() {
    }

    @Override
    public void login() {
        checkViewAttached();
        LoadingUtils.showLoadingDialog(getView().getContext());
        LoginNetWork.getInstance().phoneLogin(getView().getPhone(), getView().getCodes(), new ILoginListener() {
            @Override
            public void onLoginSuccess(String message) {
                LoadingUtils.dismissLoadingDialog();
                loadUserInfo();
            }

            @Override
            public void onNotAvailable(Account account) {
                LoadingUtils.dismissLoadingDialog();
                getView().startRegisterView();
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

    @Override
    public void activateAccount() {
        checkViewAttached();
        LoadingUtils.showLoadingDialog(getView().getContext());
        LoginNetWork.getInstance().activateAccount(getView().getPhone(), getView().getCodes(), new IActivateAccountListener() {
            @Override
            public void onActivateAccount(String message) {
                MessageUtils.shortToast(message);
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

    @Override
    public void resendSSCode() {
        LoginNetWork.getInstance().sendSSCode(getView().getPhone(), new ISSCodeListener() {
            @Override
            public void loadSSCode(String message) {
                MessageUtils.shortToast(message);
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }
}
