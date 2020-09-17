package com.duobang.middleware.fragment;

import com.duobang.middleware.contract.DefaultContract;
import com.duobang.middleware.contract.DefaultPresenter;
import com.duobang.pms_lib.framework.BaseLibFragment;

/**
 * 不需要逻辑处理的简单Fragment
 * 需要在子Fragment实现{@link DefaultContract.View}
 */
public abstract class DefaultFragment extends BaseLibFragment<DefaultPresenter, DefaultContract.View> implements DefaultContract.View{

}
