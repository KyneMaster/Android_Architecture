package com.android.architecture.core.update.imp;

import android.content.Context;

import com.android.architecture.core.update.Update;
import com.android.architecture.i.update.IUpdateListener;
import com.android.architecture.i.update.IUpdateNetApi;
import com.duobang.pms_lib.context.ApplicationContext;
import com.duobang.pms_lib.network.HttpClient;
import com.duobang.pms_lib.network.utils.HttpExceptionUtils;
import com.duobang.pms_lib.utils.JsonUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateNetWork {
    private static volatile UpdateNetWork instance;
    private IUpdateNetApi mIUpdateNetApi;
    private CompositeDisposable compositeDisposable;

    public static UpdateNetWork getInstance() {
        if (instance == null) {
            synchronized (UpdateNetWork.class) {
                if (instance == null) {
                    instance = new UpdateNetWork();
                }
            }
        }
        return instance;
    }

    private UpdateNetWork() {
        initNetWork();
        compositeDisposable = new CompositeDisposable();
    }

    private void initNetWork() {
        Context applicationContext = ApplicationContext.getInstance().getContext();
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpClient.getInstance(applicationContext))
                .baseUrl("http://api.bq04.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mIUpdateNetApi = retrofit.create(IUpdateNetApi.class);
    }

    public void loadAppInfo(String appId, String api_token, final IUpdateListener listener) {
        mIUpdateNetApi.getAppInfo(appId, api_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Response<ResponseBody> responses) {
                        if (responses.code() == 200){
                            try {
                                String json = responses.body().string();
                                Update update = JsonUtil.toObj(json, Update.class);
                                listener.onUpdateSuccess(update);
                            }catch (Exception e){
                                listener.onFailure("检测更新解析失败！");
                            }
                        }else {
                            listener.onFailure("检测更新获取失败！");
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

    public void dispose() {

        compositeDisposable.dispose();
    }

}
