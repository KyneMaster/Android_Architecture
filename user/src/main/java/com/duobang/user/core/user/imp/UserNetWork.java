package com.duobang.user.core.user.imp;

import com.duobang.pms_lib.context.ApplicationContext;
import com.duobang.pms_lib.core.network.DuobangResponse;
import com.duobang.pms_lib.core.network.ResponseCode;
import com.duobang.pms_lib.network.RetrofitCreator;
import com.duobang.pms_lib.network.utils.HttpExceptionUtils;
import com.duobang.user.core.login.User;
import com.duobang.user.core.user.parser.UserParser;
import com.duobang.user.i.user.IUserAvatarFileListener;
import com.duobang.user.i.user.IUpdateUserListener;
import com.duobang.user.i.user.IUserInfoListener;
import com.duobang.user.i.user.IUserNetApi;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserNetWork {

    private static final String TAG = "UserNetWork";
    private static volatile UserNetWork instance;
    private IUserNetApi mIUserNetApi;
    private CompositeDisposable compositeDisposable;

    public static UserNetWork getInstance() {
        if (instance == null) {
            synchronized (UserNetWork.class) {
                if (instance == null) {
                    instance = new UserNetWork();
                }
            }
        }
        return instance;
    }

    public static String getTAG() {
        return TAG;
    }

    private UserNetWork() {
        initNetWork();
        compositeDisposable = new CompositeDisposable();
    }

    private void initNetWork() {
        mIUserNetApi = RetrofitCreator.getRetrofit(ApplicationContext.getInstance().getContext()).create(IUserNetApi.class);
    }

    public void loadPersonInfo(final IUserInfoListener listener) {
        mIUserNetApi.loadPersonInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<User> responses) {
                        if (responses.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onUserInfo(responses.getData());
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

    public void loadUserInfo(String userId, final IUserInfoListener listener) {
        mIUserNetApi.loadUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<User> responses) {
                        if (responses.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onUserInfo(responses.getData());
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

    public void uploadAvatarFile(String photoPath, final IUserAvatarFileListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        File file = new File(photoPath);
        builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        MultipartBody body = builder.build();
        mIUserNetApi.uploadAvatarFile(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<String> response) {
                        if (response.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onAvatarUrl(response.getData());
                        }else {
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

    public void updateUserAvatar(String userId, String avatar, final IUpdateUserListener listener) {
        String body = UserParser.getUserAvatarBody(userId, avatar);
        assert body != null;
        mIUserNetApi.updateUserAvatar(
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
                            listener.onUpdatedSuccess(response.getData());
                        }else {
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

    public void updateNickName(String userId, String nickname, final IUpdateUserListener listener) {
        String body = UserParser.getNickNameBody(userId, nickname);
        assert body != null;
        mIUserNetApi.updateNickName(
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
                            listener.onUpdatedSuccess(response.getData());
                        }else {
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

    public void updatePassword(String userId, String oldPassword, String newPassword, final IUpdateUserListener listener) {
        String body = UserParser.getPasswordBody(userId, oldPassword, newPassword);
        assert body != null;
        mIUserNetApi.updateUserPassword(
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
                            listener.onUpdatedSuccess(response.getData());
                        }else {
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

    public void updatePasswordByPhone(String captcha, String newPassword, final IUpdateUserListener listener) {
        String body = UserParser.getPhonePasswordBody(captcha, newPassword);
        assert body != null;
        mIUserNetApi.updatePasswordByPhone(
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
                            listener.onUpdatedSuccess(response.getData());
                        }else {
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
