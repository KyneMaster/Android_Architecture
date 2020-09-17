package com.duobang.user.register;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.duobang.middleware.activity.BaseActivity;
import com.duobang.middleware.router.AppRoute;
import com.duobang.user.R;
import com.duobang.user.register.contract.RegisterContract;
import com.duobang.user.register.presenter.RegisterPresenter;

public class RegisterActivity extends BaseActivity<RegisterPresenter, RegisterContract.View> implements RegisterContract.View, TextView.OnEditorActionListener {

    private EditText account, nickname, password;
    private TextView phone;
    private Button registerBtn;
    private String phoneNumber;
    private boolean isAccount = false;
    private boolean isPhone = false;
    private boolean isNickname = false;
    private boolean isPassword = false;

    @Override
    protected int getContentViewResId() {
        return R.layout.user_activity_register;
    }

    @Override
    protected void initContent() {
        ImageView close = findViewById(R.id.close_register);
        close.setOnClickListener(getOnClickListener());
        account = findViewById(R.id.input_account_register);
        phone = findViewById(R.id.input_phone_register);
        nickname = findViewById(R.id.input_nickname_register);
        password = findViewById(R.id.input_password_register);
        ImageView clear = findViewById(R.id.clear_account_register);
        clear.setOnClickListener(getOnClickListener());
        registerBtn = findViewById(R.id.btn_register);
        registerBtn.setOnClickListener(getOnClickListener());
    }

    @Override
    protected void initData() {
        initPhone();
        initRegisterButton();
        initEditAction();
    }

    @Override
    public RegisterPresenter onCreatePresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected boolean isStatusBarLight() {
        return true;
    }

    @Override
    protected boolean handleIntent() {
        phoneNumber = getIntent().getStringExtra("phone");
        return !TextUtils.isEmpty(phoneNumber);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_register) {
            hideIME();
            finish();
        } else if (v.getId() == R.id.clear_account_register) {
            account.setText(null);
        } else if (v.getId() == R.id.btn_register) {
            getPresenter().start();
        }
    }

    private void initEditAction() {
        account.addTextChangedListener(new RegisterTextChanged(account));
        nickname.addTextChangedListener(new RegisterTextChanged(nickname));
        password.addTextChangedListener(new RegisterTextChanged(password));
        account.setOnEditorActionListener(this);
        nickname.setOnEditorActionListener(this);
        password.setOnEditorActionListener(this);
    }

    private boolean canCommit() {
        return isAccount && isPhone && isNickname && isPassword;
    }

    private void initRegisterButton() {
        if (canCommit()) {
            registerBtn.getBackground().setAlpha(255);
            registerBtn.setClickable(true);
        } else {
            registerBtn.getBackground().setAlpha(80);
            registerBtn.setClickable(false);
        }
    }

    private void initPhone() {
        isPhone = phoneNumber != null;
        phone.setText(phoneNumber);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
            if (v.getId() == R.id.input_account_register) {
                nickname.requestFocus();
            } else if (v.getId() == R.id.input_nickname_register) {
                password.requestFocus();
            } else if (v.getId() == R.id.input_password_register) {
                if (canCommit()) {
                    getPresenter().start();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getUserName() {
        return account.getText().toString().trim();
    }

    @Override
    public String getPhone() {
        return phone.getText().toString().trim();
    }

    @Override
    public String getNickName() {
        return nickname.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return password.getText().toString().trim();
    }

    @Override
    public void startMainView() {
        AppRoute.startMainView(this);
    }

    public class RegisterTextChanged implements TextWatcher {

        private EditText editText;

        RegisterTextChanged(EditText editText) {
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
            if (editText.getId() == R.id.input_account_register) {
                isAccount = s != null;
                initRegisterButton();
            } else if (editText.getId() == R.id.input_nickname_register) {
                isNickname = s != null;
                initRegisterButton();
            } else if (editText.getId() == R.id.input_password_register) {
                isPassword = s != null && s.toString().trim().length() > 5;
                initRegisterButton();
            }
        }
    }

}
