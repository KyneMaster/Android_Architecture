package com.duobang.user.login.contract;

import com.duobang.pms_lib.i.framework.IBaseView;

public interface IPhoneLoginView extends IBaseView {

    String getPhone();

    void startSSCodeView(String phone);

}
