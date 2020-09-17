package com.duobang.middleware.fragment;

import com.duobang.pms_lib.framework.BaseLibFragment;
import com.duobang.pms_lib.i.framework.IBaseView;
import com.duobang.pms_lib.i.framework.IPresenter;

public abstract class BaseFragment<P extends IPresenter<V>, V extends IBaseView> extends BaseLibFragment<P, V> {

}
