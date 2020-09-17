package com.duobang.pms_lib.i.framework;

public interface PresenterFactory<T extends IPresenter> {

    T create();
}