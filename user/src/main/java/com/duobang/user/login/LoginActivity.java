package com.duobang.user.login;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duobang.middleware.router.AppRoute;
import com.duobang.pms_lib.immersionBar.ImmersionBar;
import com.duobang.pms_lib.utils.DuobangLog;
import com.duobang.pms_lib.utils.IMEUtils;
import com.duobang.user.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

@Route(path = AppRoute.USER_LOGIN)
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView switchLoginWay;
    private List<Fragment> fragments;
    private AccountLoginFragment accountLoginFragment;
    private PhoneLoginFragment phoneLoginFragment;
    /**
     * 0: 账号密码
     * 1: 手机号验证码
     */
    private int currentIndex = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .navigationBarColor(R.color.white)
                .statusBarDarkFont(true)
                .init();
        setContentView(R.layout.user_activity_login);
        initContent();
        initData();
    }

    protected void initContent() {
        ImageView close = findViewById(R.id.close_login);
        switchLoginWay = findViewById(R.id.switch_login_may);
        TextView getBackPwd = findViewById(R.id.get_back_pwd_login);
        TextView moreOption = findViewById(R.id.more_option_login);
        close.setOnClickListener(this);
        switchLoginWay.setOnClickListener(this);
        getBackPwd.setOnClickListener(this);
        moreOption.setOnClickListener(this);
    }

    protected void initData() {
        fragments = getLoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container_login, accountLoginFragment).commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_login){
            finish();
        } else if (v.getId() == R.id.switch_login_may){
            switchLoginWay();
        } else if (v.getId() == R.id.get_back_pwd_login){
            startGetBackPwdView();
        } else if (v.getId() == R.id.more_option_login){
            DuobangLog.d("accountLogin", "更多选项");
        }
    }

    private List<Fragment> getLoginFragment() {
        fragments = new ArrayList<>();
        accountLoginFragment = new AccountLoginFragment();
        phoneLoginFragment = new PhoneLoginFragment();
        fragments.add(accountLoginFragment);
        fragments.add(phoneLoginFragment);
        return fragments;
    }

    private void switchLoginWay() {
        if (currentIndex == 0) {
            switchLoginView(1);
            switchLoginWay.setText(getString(R.string.user_login_account));
        }else {
            switchLoginView(0);
            switchLoginWay.setText(getString(R.string.user_login_phone));
        }
    }

    private void startGetBackPwdView() {
        // TODO 无内容页面隐藏
//        startActivity(new Intent(this, GetBackPwdActivity.class));
    }

    public void switchLoginView(int targetIndex) {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = fragments.get(currentIndex);
        Fragment targetFragment = fragments.get(targetIndex);
        if (!targetFragment.isAdded()) {
            mTransaction.add(R.id.container_login, targetFragment).hide(currentFragment).commit();
        } else {
            mTransaction.show(targetFragment).hide(currentFragment).commit();
        }
        currentIndex = targetIndex;
    }

    private void hideIME(){
        if (IMEUtils.isIMEAlive(this)){
            IMEUtils.hideIME(this, Objects.requireNonNull(getCurrentFocus()));
        }
    }

}
