package com.duobang.middleware.contract;

import com.duobang.pms_lib.framework.BasePresenter;

/**
 * 简单的Activity,不需要Contract
 */
public class DefaultPresenter extends BasePresenter<DefaultContract.View> implements DefaultContract.Presenter {
    @Override
    protected void onStart() {

    }
}
