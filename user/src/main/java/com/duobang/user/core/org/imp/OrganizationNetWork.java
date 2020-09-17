package com.duobang.user.core.org.imp;

import com.duobang.pms_lib.context.ApplicationContext;
import com.duobang.pms_lib.core.network.DuobangResponse;
import com.duobang.pms_lib.core.network.ResponseCode;
import com.duobang.pms_lib.network.RetrofitCreator;
import com.duobang.pms_lib.network.utils.HttpExceptionUtils;
import com.duobang.user.core.login.User;
import com.duobang.user.core.org.OrgWrapper;
import com.duobang.user.core.org.Organization;
import com.duobang.user.core.org.OrganizationInfo;
import com.duobang.user.i.org.IGroupUserListener;
import com.duobang.user.i.org.IOrgInfoListener;
import com.duobang.user.i.org.IOrgWrapperListener;
import com.duobang.user.i.org.IOrganizationNetApi;
import com.duobang.user.i.org.IPersonOrgListener;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrganizationNetWork {

    private static final String TAG = "OrganizationNetWork";
    private static volatile OrganizationNetWork instance;
    private IOrganizationNetApi mIOrganizationNetApi;
    private CompositeDisposable compositeDisposable;

    public static OrganizationNetWork getInstance() {
        if (instance == null) {
            synchronized (OrganizationNetWork.class) {
                if (instance == null) {
                    instance = new OrganizationNetWork();
                }
            }
        }
        return instance;
    }

    public static String getTAG() {
        return TAG;
    }

    private OrganizationNetWork() {
        initNetWork();
        compositeDisposable = new CompositeDisposable();
    }

    private void initNetWork() {
        mIOrganizationNetApi = RetrofitCreator.getRetrofit(ApplicationContext.getInstance().getContext()).create(IOrganizationNetApi.class);
    }

    public void loadPersonOrg(final IPersonOrgListener listener) {
        mIOrganizationNetApi.loadPersonOrg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<Organization>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<Organization> responses) {
                        if (responses.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onLoadPersonOrg(responses.getData());
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

    public void joinOrganization(String code, final IOrgInfoListener listener) {
        mIOrganizationNetApi.joinOrgByInvitationCode(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<OrganizationInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<OrganizationInfo> responses) {
                        if (responses.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onLoadOrgInfo(responses.getData());
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

    public void loadOrgGroupUserWrapper(String orgId, boolean hasRole, final IOrgWrapperListener listener) {
        mIOrganizationNetApi.getOrgGroupUserWrapper(orgId, hasRole)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<OrgWrapper>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<OrgWrapper> responses) {
                        if (responses.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onOrgGroupUserWrapper(responses.getData());
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

    public void loadOrgGroupUsers(String groupId, final IGroupUserListener listener) {
        mIOrganizationNetApi.getOrgGroupUsers(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<List<User>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<List<User>> responses) {
                        if (responses.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onGroupUserList(responses.getData());
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

    public void switchHomeOrg(String orgId, final IOrgInfoListener listener) {
        mIOrganizationNetApi.updateHomeOrg(orgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DuobangResponse<OrganizationInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DuobangResponse<OrganizationInfo> responses) {
                        if (responses.getCode().equals(ResponseCode.SUCCESS)){
                            listener.onLoadOrgInfo(responses.getData());
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

    public void dispose(){

        compositeDisposable.dispose();
    }
}
