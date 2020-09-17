package com.duobang.user.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.duobang.middleware.fragment.BaseFragment;
import com.duobang.middleware.router.AppRoute;
import com.duobang.user.R;
import com.duobang.middleware.constant.IUserConstant;
import com.duobang.user.login.contract.IAccountLoginView;
import com.duobang.user.login.presenter.AccountLoginPresenter;

public class AccountLoginFragment extends BaseFragment<AccountLoginPresenter, IAccountLoginView> implements IAccountLoginView, TextView.OnEditorActionListener {

    private EditText account, password;
    private CheckBox show;
    private Button loginBtn;
    private boolean isAccount = false;
    private boolean isPassword = false;

    @Override
    protected int setLayoutResID() {
        return R.layout.user_fragment_account_login;
    }

    @Override
    protected void initView() {
        account = findViewById(R.id.input_account_login);
        password = findViewById(R.id.input_password_login);
        ImageView clear = findViewById(R.id.clear_account_login);
        show = findViewById(R.id.show_password_login);
        loginBtn = findViewById(R.id.btn_account_login);
        clear.setOnClickListener(getOnClickListener());
        loginBtn.setOnClickListener(getOnClickListener());
        initEditAction();
        initPasswordShow();
        setLoginButton();
    }

    @Override
    public AccountLoginPresenter onCreatePresenter() {
        return new AccountLoginPresenter();
    }

    @Override
    protected void onStartLoadData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.clear_account_login) {
            account.setText(null);
        } else if (v.getId() == R.id.btn_account_login) {
            getPresenter().start();
        }
    }

    @Override
    public String getAccount() {
        return account.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return password.getText().toString().trim();
    }

    @Override
    public void startActivateView(String phone) {
        Intent intent = new Intent(getActivity(), SSCodeActivity.class);
        intent.putExtra("type", IUserConstant.INVITATION_TYPE.ACCOUNT_ACTIVATE);
        intent.putExtra("phone", phone);
        startActivity(intent);
    }

    @Override
    public void startMainView() {
        AppRoute.startMainView(getActivity());
    }

    private void initEditAction() {
        account.addTextChangedListener(new LoginTextChanged(account));
        password.addTextChangedListener(new LoginTextChanged(password));
        account.setOnEditorActionListener(this);
        password.setOnEditorActionListener(this);
    }

    /**
     * 点击小眼睛控制密码显隐
     */
    private void initPasswordShow() {
        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password.setSelection(password.getText().toString().length());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setSelection(password.getText().toString().length());
                }
            }
        });
    }

    private void setLoginButton() {
        if (canLogin()) {
            loginBtn.getBackground().setAlpha(255);
            loginBtn.setClickable(true);
        } else {
            loginBtn.getBackground().setAlpha(80);
            loginBtn.setClickable(false);
        }
    }

    private boolean canLogin() {
        return isAccount && isPassword;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
            if (v.getId() == R.id.input_account_login) {
                password.requestFocus();
            } else if (v.getId() == R.id.input_password_login) {
                if (canLogin()) {
                    getPresenter().start();
                }
            }
            return true;
        }
        return false;
    }

    public class LoginTextChanged implements TextWatcher {

        private EditText editText;

        LoginTextChanged(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (editText.getId() == R.id.input_account_login) {
                isAccount = s != null && !s.toString().equals("");
                setLoginButton();
            } else if (editText.getId() == R.id.input_password_login) {
                isPassword = s != null && !s.toString().equals("");
                setLoginButton();
            }
        }
    }
}
