package com.duobang.user.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.duobang.middleware.activity.BaseActivity;
import com.duobang.middleware.constant.IUserConstant;
import com.duobang.middleware.router.AppRoute;
import com.duobang.middleware.weight.SSCodeInputView;
import com.duobang.user.R;
import com.duobang.user.login.contract.ISSCodeView;
import com.duobang.user.login.presenter.SSCodePresenter;
import com.duobang.user.register.RegisterActivity;

public class SSCodeActivity extends BaseActivity<SSCodePresenter, ISSCodeView> implements ISSCodeView {

    private String phone;
    private int type;
    private TextView phoneTv;
    private TextView tip;
    private SSCodeInputView inputView;
    private String codes;

    @Override
    protected int getContentViewResId() {
        return R.layout.user_activity_sscode;
    }

    @Override
    protected void initContent() {
        ImageView back = findViewById(R.id.back_sscode);
        phoneTv = findViewById(R.id.phone_sscode);
        inputView = findViewById(R.id.input_sscode);
        TextView help = findViewById(R.id.help_sscode);
        TextView resend = findViewById(R.id.resend_sscode);
        tip = findViewById(R.id.tip_sscode);
        resend.setOnClickListener(getOnClickListener());
        help.setOnClickListener(getOnClickListener());
        back.setOnClickListener(getOnClickListener());
        initTip(type);
        initPhone(getPhone());
        initInputAction();
    }

    private void initTip(int type) {
        if (type == IUserConstant.INVITATION_TYPE.ACCOUNT_ACTIVATE){
            tip.setText(getString(R.string.user_sscode_activate_account));
            findViewById(R.id.activate_tip_sscode).setVisibility(View.VISIBLE);
        } else if (type == IUserConstant.INVITATION_TYPE.PHONE_VERIFY){
            tip.setText(getString(R.string.user_sscode_send_success));
        }
    }

    private void initInputAction() {
        inputView.setOnInputViewCallback(new SSCodeInputView.OnInputViewCallback() {
            @Override
            public void putCodes(String codeList) {
                codes = codeList;
                hideIME();
                if (type == IUserConstant.INVITATION_TYPE.ACCOUNT_ACTIVATE){
                    getPresenter().activateAccount();
                } else if (type == IUserConstant.INVITATION_TYPE.PHONE_VERIFY){
                    getPresenter().login();
                }
            }
        });
    }

    @Override
    protected boolean handleIntent() {
        phone = getIntent().getStringExtra("phone");
        type = getIntent().getIntExtra("type", -1);
        return !TextUtils.isEmpty(phone);
    }

    @Override
    public SSCodePresenter onCreatePresenter() {
        return new SSCodePresenter();
    }

    @Override
    protected boolean isStatusBarLight() {
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_sscode){
            finish();
        } else if (v.getId() == R.id.help_sscode){
            startSSCodeHelpView();
        } else if (v.getId() == R.id.resend_sscode){
            getPresenter().resendSSCode();
        }
    }

    private void startSSCodeHelpView() {
        startActivity(new Intent(this, SSCodeHelperActivity.class));
    }

    private void initPhone(String phone) {
        String phoneStr = "+86 " + phone;
        phoneTv.setText(phoneStr);
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getCodes() {
        return codes;
    }

    @Override
    public void startMainView() {
        AppRoute.startMainView(this);
    }

    @Override
    public void startRegisterView() {
        hideIME();
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("phone", getPhone());
        startActivity(intent);
        finish();
    }
}
