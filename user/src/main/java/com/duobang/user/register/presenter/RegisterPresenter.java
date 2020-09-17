package com.duobang.user.register.presenter;

import com.duobang.middleware.cache.PreferenceManager;
import com.duobang.middleware.constant.IUserConstant;
import com.duobang.pms_lib.framework.BasePresenter;
import com.duobang.pms_lib.utils.MessageUtils;
import com.duobang.user.core.login.User;
import com.duobang.user.core.login.imp.LoginNetWork;
import com.duobang.user.i.login.IRegisterListener;
import com.duobang.user.register.contract.RegisterContract;

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    @Override
    protected void onStart() {
        checkViewAttached();
        register();
    }

    @Override
    public void register() {
        LoginNetWork.getInstance().registerUser(getView().getUserName()
                , getView().getPhone()
                , getView().getNickName()
                , getView().getPassword(), new IRegisterListener() {
            @Override
            public void onRegisterSuccess(User user) {
                saveUserInfo(user);
                if (user.getState() == IUserConstant.USER_STATE.NOT_ACTIVATION){
                    MessageUtils.shortToast("该用户未激活！！");
                } else if (user.getState() == IUserConstant.USER_STATE.AVAILABLE){
                    getView().startMainView();
                } else if (user.getState() == IUserConstant.USER_STATE.DISABLE){
                    MessageUtils.shortToast("该用户已被禁用！！");
                } else if (user.getState() == IUserConstant.USER_STATE.DELETED){
                    MessageUtils.shortToast("该用户已被删除，不可用！！");
                }
            }

            @Override
            public void onFailure(String message) {
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
}
