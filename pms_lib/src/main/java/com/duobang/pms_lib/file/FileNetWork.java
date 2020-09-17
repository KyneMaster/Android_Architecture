package com.duobang.pms_lib.file;

import com.duobang.pms_lib.context.ApplicationContext;
import com.duobang.pms_lib.core.network.DuobangResponse;
import com.duobang.pms_lib.core.network.ResponseCode;
import com.duobang.pms_lib.network.RetrofitCreator;
import com.duobang.pms_lib.network.utils.HttpExceptionUtils;

import java.io.File;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileNetWork {
    private static volatile FileNetWork instance;
    private IFileNetApi mIFileNetApi;
    private CompositeDisposable compositeDisposable;

    public static FileNetWork getInstance() {
        if (instance == null) {
            synchronized (FileNetWork.class) {
                if (instance == null) {
                    instance = new FileNetWork();
                }
            }
        }
        return instance;
    }

    private FileNetWork() {
        initNetWork();
        compositeDisposable = new CompositeDisposable();
    }

    private void initNetWork() {
        mIFileNetApi = RetrofitCreator.getRetrofit(ApplicationContext.getInstance().getContext()).create(IFileNetApi.class);
    }

    public void uploadPhotoList(List<String> photoPaths, final IPhotoListListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (photoPaths != null && photoPaths.size() > 0) {
            for (int i = 0; i < photoPaths.size(); i++) {
                File file = new File(photoPaths.get(i));
                builder.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }
        }
        MultipartBody body = builder.build();
        mIFileNetApi.uploadPhotoList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<List<String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<List<String>> responses) {
                        if (responses.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onUploadListOk(responses.getData());
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

    public void uploadFileList(List<String> filePaths, final IFileListListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (filePaths != null && filePaths.size() > 0) {
            for (int i = 0; i < filePaths.size(); i++) {
                File file = new File(filePaths.get(i));
                builder.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
        }
        MultipartBody body = builder.build();
        mIFileNetApi.uploadFileList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<List<DuobangFile>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<List<DuobangFile>> responses) {
                        if (responses.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onFileSuccess(responses.getData());
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

    public void dispose() {

        compositeDisposable.dispose();
    }
}
