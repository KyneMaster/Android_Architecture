package com.duobang.user.personal.presenter;

import android.os.Handler;
import android.os.Message;

import com.duobang.middleware.cache.PreferenceManager;
import com.duobang.middleware.environment.AppConfig;
import com.duobang.pms_lib.framework.BasePresenter;
import com.duobang.pms_lib.utils.MessageUtils;
import com.duobang.user.core.login.User;
import com.duobang.user.core.org.Organization;
import com.duobang.user.core.org.imp.OrganizationNetWork;
import com.duobang.user.core.user.imp.UserNetWork;
import com.duobang.user.i.org.IPersonOrgListener;
import com.duobang.user.i.user.IUserAvatarFileListener;
import com.duobang.user.i.user.IUpdateUserListener;
import com.duobang.user.i.user.IUserInfoListener;
import com.duobang.user.personal.contract.PersonalContract;

import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PersonalPresenter extends BasePresenter<PersonalContract.View> implements PersonalContract.Presenter {

    private String compressPath;
    private String ossPath;

    private Handler handler = getHandler();
    private static final int START_UPLOAD = 1001;
    private static final int START_UPDATE = 1002;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case START_UPLOAD:
                uploadAvatarFile();
                return true;
            case START_UPDATE:
                updateAvatar();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onStart() {
        checkViewAttached();
        getPersonalInfo();
        getOrganizationInfo();
    }

    @Override
    public void getPersonalInfo() {
        UserNetWork.getInstance().loadPersonInfo(new IUserInfoListener() {
            @Override
            public void onUserInfo(User user) {
                PreferenceManager.getInstance().getUserPreferences().saveUserAvatar(user.getAvatar());
                getView().setupUser(user);
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }

    @Override
    public void getOrganizationInfo() {
        OrganizationNetWork.getInstance().loadPersonOrg(new IPersonOrgListener() {
            @Override
            public void onLoadPersonOrg(Organization org) {
                getView().setupCompany(org);
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }

    @Override
    public void updateAvatar(String avatarPath) {
        compressImage(avatarPath);
    }

    @Override
    public void updateNickName(String nickName) {
        final String userId = PreferenceManager.getInstance().getUserPreferences().getUserId();
        UserNetWork.getInstance().updateNickName(userId, nickName, new IUpdateUserListener() {
            @Override
            public void onUpdatedSuccess(User user) {
                getView().setupUser(user);
                PreferenceManager.getInstance().getUserPreferences().saveNickName(user.getNickname());
                MessageUtils.shortToast("修改成功");
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }

    private void uploadAvatarFile() {
        UserNetWork.getInstance().uploadAvatarFile(compressPath, new IUserAvatarFileListener() {
            @Override
            public void onAvatarUrl(String url) {
                ossPath = url;
                handler.sendEmptyMessage(START_UPDATE);
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }

    private void updateAvatar() {
        String userId = PreferenceManager.getInstance().getUserPreferences().getUserId();
        UserNetWork.getInstance().updateUserAvatar(userId, ossPath, new IUpdateUserListener() {

            @Override
            public void onUpdatedSuccess(User user) {
                PreferenceManager.getInstance().getUserPreferences().saveUserAvatar(user.getAvatar());
                getView().setupUser(user);
                MessageUtils.shortToast("修改成功");
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }

    private void compressImage(final String path) {
        Luban.with(getView().getContext()).load(new File(path)).ignoreBy(400).setTargetDir(AppConfig.getPathDir()).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(File file) {
                compressPath = file.getPath();
                handler.sendEmptyMessage(START_UPLOAD);
            }

            @Override
            public void onError(Throwable e) {
                compressPath = path;
                handler.sendEmptyMessage(START_UPLOAD);
            }
        }).launch();
    }

}
