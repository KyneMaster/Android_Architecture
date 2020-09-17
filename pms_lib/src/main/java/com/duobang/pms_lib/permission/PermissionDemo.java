package com.duobang.pms_lib.permission;

import android.Manifest;
import android.widget.Toast;

import com.duobang.pms_lib.i.permission.PermissionCallBack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 动态权限申请使用Demo
 */
public class PermissionDemo extends AppCompatActivity implements PermissionCallBack {

    private DuobangPermission mDuobangPermission;

    private void checkNeedPermission(int code) {
        mDuobangPermission = DuobangPermission
                .with(PermissionDemo.this)
                .addPermissions(Manifest.permission.CAMERA)
                .addPermissionCode(code)
                .requestGrant(this)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mDuobangPermission.onPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void granted() {
        Toast.makeText(this, "11111111111", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void grantSuccess() {
        Toast.makeText(this, "22222222222", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void grantFail(String onError) {
        Toast.makeText(this, "33333333333", Toast.LENGTH_SHORT).show();
    }
}
