package com.duobang.user.register.contract;

import com.duobang.pms_lib.i.framework.IBaseView;

public interface RegisterContract {

    interface Presenter {

        void register();
    }

    interface View extends IBaseView{

        String getUserName();
        String getPhone();
        String getNickName();
        String getPassword();

        void startMainView();
    }
}
