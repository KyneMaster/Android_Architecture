package com.android.architecture.splash;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import com.android.architecture.R;
import com.android.architecture.framework.MainActivity;
import com.android.architecture.splash.contract.ISplashContract;
import com.android.architecture.splash.presenter.SplashPresenter;
import com.duobang.middleware.activity.BaseActivity;
import com.duobang.pms_lib.i.permission.PermissionCallBack;
import com.duobang.pms_lib.immersionBar.ImmersionBar;
import com.duobang.pms_lib.permission.DuobangPermission;
import com.duobang.user.login.LoginActivity;

import androidx.annotation.NonNull;

public class SplashActivity extends BaseActivity<SplashPresenter, ISplashContract.View> implements ISplashContract.View {

    private DuobangPermission mDuobangPermission;
    private final static int PERMISSION_CODE = 100;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initContent() {
        getPresenter().start();
    }

    @Override
    public SplashPresenter onCreatePresenter() {
        return new SplashPresenter();
    }

    @Override
    public void doFullScreen() {
        ImmersionBar.with(this).fullScreen(true).init();
    }

    @Override
    protected boolean isStatusBarLight() {
        return false;
    }

    @Override
    public void openMainView() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void openLoginView() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void requestPermission() {
        mDuobangPermission = DuobangPermission.with(this)
                .addPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .addPermissionCode(PERMISSION_CODE)
                .requestGrant(new PermissionCallBack() {
                    @Override
                    public void granted() {
                        getPresenter().openMainView();
                    }

                    @Override
                    public void grantSuccess() {
                        getPresenter().openMainView();
                    }

                    @Override
                    public void grantFail(String onError) {
                        finish();
                    }
                }).build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mDuobangPermission.onPermissionResult(requestCode, permissions, grantResults);
    }
}
