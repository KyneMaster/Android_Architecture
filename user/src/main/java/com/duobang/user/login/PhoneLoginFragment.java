package com.duobang.user.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import com.duobang.user.login.contract.IPhoneLoginView;
import com.duobang.user.login.presenter.PhoneLoginPresenter;

public class PhoneLoginFragment extends BaseFragment<PhoneLoginPresenter, IPhoneLoginView> implements IPhoneLoginView{

    private EditText phone;
    private CheckBox readContract;
    private Button loginBtn;
    private boolean isRead = true;
    private boolean isPhone = false;

    @Override
    protected int setLayoutResID() {
        return R.layout.user_fragment_phone_login;
    }

    @Override
    protected void initView() {
        phone = findViewById(R.id.input_phone_login);
        ImageView clear = findViewById(R.id.clear_phone_login);
        readContract = findViewById(R.id.read_contract);
        TextView contractServer = findViewById(R.id.contract_server_phone_login);
        TextView contractPrivate = findViewById(R.id.contract_private_phone_login);
        loginBtn = findViewById(R.id.btn_phone_login);
        loginBtn.setOnClickListener(getOnClickListener());
        clear.setOnClickListener(getOnClickListener());
        contractServer.setOnClickListener(getOnClickListener());
        contractPrivate.setOnClickListener(getOnClickListener());
        initButtonAlpha();
        initReadContract();
        initEditAction();
    }

    @Override
    public PhoneLoginPresenter onCreatePresenter() {
        return new PhoneLoginPresenter();
    }

    @Override
    protected void onStartLoadData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.clear_phone_login){
            phone.setText(null);
        } else if (v.getId() == R.id.btn_phone_login){
            getPresenter().start();
        } else if (v.getId() == R.id.contract_server_phone_login){
            startContractView();
        } else if (v.getId() == R.id.contract_private_phone_login){
            startContractView();
        }
    }

    private void startContractView() {
        AppRoute.openContractView(getActivity());
    }

    @Override
    public String getPhone() {
        return phone.getText().toString().trim();
    }

    @Override
    public void startSSCodeView(String phone) {
        Intent intent = new Intent(getActivity(), SSCodeActivity.class);
        intent.putExtra("type", IUserConstant.INVITATION_TYPE.PHONE_VERIFY);
        intent.putExtra("phone", phone);
        startActivity(intent);
    }

    private void initEditAction() {
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isPhone = s != null && !s.toString().equals("") && s.toString().length() == 11;
                if (isRead && isPhone){
                    loginBtn.getBackground().setAlpha(255);
                    loginBtn.setClickable(true);
                } else {
                    loginBtn.getBackground().setAlpha(80);
                    loginBtn.setClickable(false);
                }
            }
        });
    }

    private void initButtonAlpha() {
        loginBtn.getBackground().setAlpha(80);
        loginBtn.setClickable(false);
    }

    private void initReadContract() {
        readContract.setChecked(true);
        readContract.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRead = isChecked;
                if (isRead && isPhone){
                    loginBtn.getBackground().setAlpha(255);
                    loginBtn.setClickable(true);
                } else {
                    loginBtn.getBackground().setAlpha(80);
                    loginBtn.setClickable(false);
                }
            }
        });
    }

}
