package com.duobang.pms_lib.framework;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.duobang.pms_lib.i.framework.IBaseView;
import com.duobang.pms_lib.i.framework.IPresenter;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IBaseView> implements IPresenter<V> {

    private Handler handler;

    /**
     * 当前连接的View
     */
    private WeakReference<V> mvpView;

    public void attachView(V mvpView) {
        this.mvpView = new WeakReference<>(mvpView);
    }

    public void detachView() {
        if(mvpView!=null){
            mvpView.clear();
            mvpView=null;
        }
    }

    @Override
    public void start() {
        onStart();
    }

    /**
     * 开始加载数据
     */
    protected abstract void onStart();

    /**
     * 是否与View建立连接
     *
     * @return
     */
    public boolean isViewAttached() {
        return mvpView != null;
    }

    /**
     * 获取当前连接的View
     *
     * @return
     */
    public V getView() {
        checkViewAttached();
        return mvpView.get();
    }

    /**
     * 每次调用业务请求的时候都要先调用方法检查是否与View建立连接，没有则抛出异常
     */
    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    public static class MvpViewNotAttachedException extends RuntimeException {

        public MvpViewNotAttachedException() {
            super("Please call attachView(mvpView) method to establish connection with view before requesting data");
        }
    }

    public Handler getHandler() {
        if (handler == null) {
            handler = new BasePresenterHandler(Looper.getMainLooper(), this);
        }
        return handler;
    }

    public boolean handleMessage(Message msg) {
        return false;
    }

    private static class BasePresenterHandler extends Handler {
        private final WeakReference<BasePresenter> outerReference;

        private BasePresenterHandler(Looper looper, BasePresenter outerReference) {
            super(looper);
            this.outerReference = new WeakReference<>(outerReference);
        }

        @Override
        public void handleMessage(Message msg) {
            if (outerReference.get() != null) {
                outerReference.get().handleMessage(msg);
            }
        }
    }
}
