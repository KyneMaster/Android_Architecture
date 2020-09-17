package com.android.architecture.splash.presenter;

import android.os.Handler;
import android.os.Message;

import com.android.architecture.splash.contract.ISplashContract;
import com.duobang.pms_lib.framework.BasePresenter;
import com.duobang.pms_lib.session.CookieProvider;

public class SplashPresenter extends BasePresenter<ISplashContract.View> implements ISplashContract.Presenter {

    private Handler handler = getHandler();
    private static final int REQUEST_PERMISSION = 1001;
    private static final int SUCCESS = 1002;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case REQUEST_PERMISSION:
                getView().requestPermission();
                return true;
            case SUCCESS:
                if (CookieProvider.getInstance().isLogin()){
                    getView().openMainView();
                } else {
                    getView().openLoginView();
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onStart() {
        handler.sendEmptyMessageDelayed(REQUEST_PERMISSION, 1000);
    }

    @Override
    public void openMainView() {
        handler.sendEmptyMessageDelayed(SUCCESS, 1000);
    }
}
