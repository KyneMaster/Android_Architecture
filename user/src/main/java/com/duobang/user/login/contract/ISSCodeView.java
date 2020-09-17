package com.duobang.user.login.contract;

import com.duobang.pms_lib.i.framework.IBaseView;

public interface ISSCodeView extends IBaseView {

    String getPhone();

    String getCodes();

    void startMainView();

    void startRegisterView();

}
