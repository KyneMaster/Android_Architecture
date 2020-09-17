package com.duobang.user.core.login.imp;

import com.duobang.pms_lib.core.network.DuobangResponse;
import com.duobang.pms_lib.core.network.ResponseCode;
import com.duobang.pms_lib.environment.DebugEnv;
import com.duobang.pms_lib.network.utils.HttpExceptionUtils;
import com.duobang.pms_lib.network.utils.ParseErrorBodyUtils;
import com.duobang.user.core.login.Account;
import com.duobang.user.core.login.User;
import com.duobang.user.core.login.parser.LoginParser;
import com.duobang.user.i.login.IActivateAccountListener;
import com.duobang.user.i.login.ILoginListener;
import com.duobang.user.i.login.ILoginNetApi;
import com.duobang.user.i.login.IRegisterListener;
import com.duobang.user.i.login.ISSCodeListener;
import com.duobang.user.interceptor.LoginHttpInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginNetWork {
    private static final String TAG = "LoginNetWork";
    private static volatile LoginNetWork instance;
    private static  Retrofit retrofit;
    private ILoginNetApi mILoginNetApi;
    private CompositeDisposable compositeDisposable;

    public static LoginNetWork getInstance() {
        if (instance == null) {
            synchronized (LoginNetWork.class) {
                if (instance == null) {
                    instance = new LoginNetWork();
                }
            }
        }
        return instance;
    }

    private LoginNetWork() {
        initNetWork();
        compositeDisposable = new CompositeDisposable();
    }

    private void initNetWork() {
        if (retrofit == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoginHttpInterceptor()).build();
            RxJava2CallAdapterFactory rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create();
            Gson gson = new GsonBuilder().setLenient().create();
            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);

            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(DebugEnv.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)
                    .build();
        }
        mILoginNetApi = retrofit.create(ILoginNetApi.class);
    }

    public void accountLogin(String username, String password, final ILoginListener listener) {
        String body = LoginParser.getAccountLoginBody(username, password);
        if (body == null) return;
        mILoginNetApi.accountLogin(
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<Account>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<Account> response) {
                        if (response.getData() != null && response.getData().isActivate()){
                            listener.onLoginSuccess(response.getMessage());
                        } else {
                            listener.onNotAvailable(response.getData());
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

    public void phoneLogin(String phone, String captcha, final ILoginListener listener) {
        String body = LoginParser.getPhoneLoginBody(phone, captcha);
        if (body == null) return;
        mILoginNetApi.phoneLogin(
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<Account>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<Account> response) {
                        if (response.getCode().equals(ResponseCode.SUCCESS)) {
                            if (response.getData() != null && response.getData().isRegistered()){
                                listener.onLoginSuccess(response.getMessage());
                            } else {
                                listener.onNotAvailable(response.getData());
                            }
                        } else {
                            listener.onFailure(response.getMessage());
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

    public void sendSSCode(String phone, final ISSCodeListener listener) {
        mILoginNetApi.loadSSCode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            try {
                                listener.loadSSCode("验证码已发送");
                            } catch (Exception e) {
                                listener.onFailure("验证码发送失败！请稍后重试！！");
                            }
                        } else {
                            String message = ParseErrorBodyUtils.parseErrorBody(response);
                            listener.onFailure(message);
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

    public void activateAccount(String phone, String captcha, final IActivateAccountListener listener) {
        String body = LoginParser.getPhoneLoginBody(phone, captcha);
        if (body == null) return;
        mILoginNetApi.activateAccount(
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse response) {
                        if (response.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onActivateAccount("激活成功！");
                        } else {
                            listener.onFailure(response.getMessage());
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

    public void registerUser(String username, String phone, String nickname, String password, final IRegisterListener listener) {
        String body = LoginParser.getRegisterBody(username, password, phone, nickname);
        if (body == null) return;
        mILoginNetApi.register(
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<User> response) {
                        if (response.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onRegisterSuccess(response.getData());
                        } else {
                            listener.onFailure(response.getMessage());
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
