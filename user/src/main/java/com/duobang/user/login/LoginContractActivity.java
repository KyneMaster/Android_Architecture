package com.duobang.user.login;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duobang.middleware.activity.DefaultActivity;
import com.duobang.middleware.contract.DefaultContract;
import com.duobang.middleware.contract.DefaultPresenter;
import com.duobang.middleware.fragment.WebViewFragment;
import com.duobang.middleware.router.AppRoute;
import com.duobang.user.R;

@Route(path = AppRoute.APP_CONTRACT)
public class LoginContractActivity extends DefaultActivity implements DefaultContract.View {

    private String url;

    @Override
    protected int getContentViewResId() {
        return R.layout.user_activity_login_contract;
    }

    @Override
    protected void initContent() {
        findViewById(R.id.close_contract).setOnClickListener(getOnClickListener());
        getSupportFragmentManager().beginTransaction().add(R.id.container_contract, WebViewFragment.newInstance(url)).commit();
    }

    @Override
    protected boolean handleIntent() {
        url = getIntent().getStringExtra("url");
        return !TextUtils.isEmpty(url);
    }

    @Override
    public DefaultPresenter onCreatePresenter() {
        return new DefaultPresenter();
    }

    @Override
    protected boolean isStatusBarLight() {
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_contract){
            finish();
        }
    }
}
