package com.duobang.user.login.presenter;

import com.duobang.pms_lib.framework.BasePresenter;
import com.duobang.pms_lib.utils.LoadingUtils;
import com.duobang.pms_lib.utils.MessageUtils;
import com.duobang.user.core.login.imp.LoginNetWork;
import com.duobang.user.i.login.ISSCodeListener;
import com.duobang.user.login.contract.IPhoneLoginPresenter;
import com.duobang.user.login.contract.IPhoneLoginView;

public class PhoneLoginPresenter extends BasePresenter<IPhoneLoginView> implements IPhoneLoginPresenter {

    @Override
    protected void onStart() {
        checkViewAttached();
        getSSCode();
    }

    @Override
    public void getSSCode() {
        LoadingUtils.showLoadingDialog(getView().getContext());
        LoginNetWork.getInstance().sendSSCode(getView().getPhone(), new ISSCodeListener() {
            @Override
            public void loadSSCode(String message) {
                LoadingUtils.dismissLoadingDialog();
                getView().startSSCodeView(getView().getPhone());
            }

            @Override
            public void onFailure(String message) {
                LoadingUtils.dismissLoadingDialog();
                MessageUtils.shortToast(message);
            }
        });
    }
}
