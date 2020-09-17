package com.duobang.user.personal.imp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.duobang.middleware.constant.IUserConstant;
import com.duobang.middleware.constant.REQUEST_CODE;
import com.duobang.pms_lib.immersionBar.ImmersionBar;
import com.duobang.pms_lib.utils.IMEUtils;
import com.duobang.user.R;

import androidx.appcompat.app.AppCompatActivity;

public class UserNameActivity extends AppCompatActivity {

    private EditText mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImmersionBar();
        setContentView(R.layout.user_activity_user_name);
        initView();
    }

    private void initView() {
        String username = getIntent().getStringExtra(IUserConstant.USER_NAME);
        findViewById(R.id.back_username).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.ok_username).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMEUtils.hideIME(UserNameActivity.this, mUserName);
                forResult();
            }
        });
        mUserName = findViewById(R.id.edit_username);
        mUserName.setText(username);
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .navigationBarColor(R.color.white)
                .statusBarDarkFont(false)
                .init();
    }

    private void forResult() {
        if (mUserName.getText() == null || mUserName.getText().toString().equals("")){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(IUserConstant.USER_NAME, mUserName.getText().toString());
        setResult(REQUEST_CODE.UPDATE_USERNAME, intent);
        finish();
    }
}
