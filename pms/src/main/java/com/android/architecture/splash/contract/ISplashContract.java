package com.android.architecture.splash.contract;

import com.duobang.pms_lib.i.framework.IBaseView;

public interface ISplashContract {

    interface Presenter{

        void openMainView();
    }

    interface View extends IBaseView{

        void requestPermission();

        void openMainView();

        void openLoginView();
    }
}
