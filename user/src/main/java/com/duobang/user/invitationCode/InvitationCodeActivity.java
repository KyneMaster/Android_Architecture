package com.duobang.user.invitationCode;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duobang.middleware.PMSApplication;
import com.duobang.middleware.activity.BaseActivity;
import com.duobang.middleware.router.AppRoute;
import com.duobang.user.R;
import com.duobang.user.invitationCode.contract.InvitationContract;
import com.duobang.user.invitationCode.presenter.InvitationPresenter;

@Route(path = AppRoute.INVITATION_CODE)
public class InvitationCodeActivity extends BaseActivity<InvitationPresenter, InvitationContract.View> implements InvitationContract.View {

    private EditText input;

    @Override
    protected int getContentViewResId() {
        return R.layout.user_activity_invitation_code;
    }

    @Override
    protected void initContent() {
        input = findViewById(R.id.input_invitation_code);
        TextView commit = findViewById(R.id.commit_invitation_code);
        commit.setOnClickListener(getOnClickListener());
        TextView logout = findViewById(R.id.logout_invitation_code);
        logout.setOnClickListener(getOnClickListener());
    }

    @Override
    public InvitationPresenter onCreatePresenter() {
        return new InvitationPresenter();
    }

    @Override
    protected boolean isStatusBarLight() {
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commit_invitation_code) {
            if (canCommit())
                getPresenter().start();
        } else if (v.getId() == R.id.logout_invitation_code) {
            ((PMSApplication) getApplication()).logout();
        }
    }

    private boolean canCommit() {
        return !TextUtils.isEmpty(input.getText().toString().trim());
    }

    @Override
    public String getInvitationCode() {
        return input.getText().toString().trim();
    }

    @Override
    public void startMainView() {
        AppRoute.startMainView(this);
    }
}
