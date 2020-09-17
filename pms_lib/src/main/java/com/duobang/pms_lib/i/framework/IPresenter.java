package com.duobang.pms_lib.i.framework;

public interface IPresenter<V extends IBaseView> {
    /**
     * Presenter与View建立连接
     *
     * @param mvpView 与此Presenter相对应的View
     */
    void attachView(V mvpView);

    /**
     * Presenter与View连接断开
     */
    void detachView();

    /**
     * 开始加载数据
     */
    void start();

}
