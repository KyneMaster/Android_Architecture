package com.duobang.user.core.login.imp;

import com.duobang.pms_lib.context.ApplicationContext;
import com.duobang.pms_lib.core.network.DuobangResponse;
import com.duobang.pms_lib.core.network.ResponseCode;
import com.duobang.pms_lib.network.RetrofitCreator;
import com.duobang.pms_lib.network.utils.HttpExceptionUtils;
import com.duobang.user.i.login.ILogoutListener;
import com.duobang.user.i.login.ILogoutNetApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LogoutNetWork {

    private static final String TAG = "LogoutNetWork";
    private static volatile LogoutNetWork instance;
    private ILogoutNetApi mILogoutNetApi;
    private CompositeDisposable compositeDisposable;

    public static LogoutNetWork getInstance() {
        if (instance == null) {
            synchronized (LogoutNetWork.class) {
                if (instance == null) {
                    instance = new LogoutNetWork();
                }
            }
        }
        return instance;
    }

    public static String getTAG() {
        return TAG;
    }

    private LogoutNetWork() {
        initNetWork();
        compositeDisposable = new CompositeDisposable();
    }

    private void initNetWork() {
        mILogoutNetApi = RetrofitCreator.getRetrofit(ApplicationContext.getInstance().getContext()).create(ILogoutNetApi.class);
    }

    public void loginOut(final ILogoutListener listener) {
        mILogoutNetApi.loginOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<Object> responses) {
                        if (responses.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onLogoutSuccess("退出登录成功！");
                        }else {
                            listener.onFailure(responses.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = HttpExceptionUtils.HandleErrorException(e).message;
                        listener.onFailure(message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
