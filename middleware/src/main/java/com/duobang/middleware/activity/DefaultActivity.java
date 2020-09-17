package com.duobang.middleware.activity;

import com.duobang.middleware.contract.DefaultContract;
import com.duobang.middleware.contract.DefaultPresenter;
import com.duobang.pms_lib.framework.BaseLibActivity;

/**
 * 不需要逻辑处理的简单Activity
 * 需要在子Activity实现{@link DefaultContract.View}
 */
public abstract class DefaultActivity extends BaseLibActivity<DefaultPresenter, DefaultContract.View> implements DefaultContract.View{

}
