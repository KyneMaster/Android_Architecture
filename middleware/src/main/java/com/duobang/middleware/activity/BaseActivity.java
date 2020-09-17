package com.duobang.middleware.activity;

import com.duobang.pms_lib.framework.BaseLibActivity;
import com.duobang.pms_lib.i.framework.IBaseView;
import com.duobang.pms_lib.i.framework.IPresenter;

public abstract class BaseActivity<P extends IPresenter<V>, V extends IBaseView> extends BaseLibActivity<P, V> {


}
