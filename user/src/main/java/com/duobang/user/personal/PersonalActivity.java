package com.duobang.user.personal;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duobang.middleware.activity.BaseActivity;
import com.duobang.middleware.common.AppImageLoader;
import com.duobang.middleware.common.AppPictureSelector;
import com.duobang.middleware.constant.IUserConstant;
import com.duobang.middleware.constant.REQUEST_CODE;
import com.duobang.middleware.router.AppRoute;
import com.duobang.middleware.weight.AvatarView;
import com.duobang.user.R;
import com.duobang.user.core.login.User;
import com.duobang.user.core.org.Organization;
import com.duobang.user.core.org.OrganizationInfo;
import com.duobang.user.personal.contract.PersonalContract;
import com.duobang.user.personal.imp.UserNameActivity;
import com.duobang.user.personal.presenter.PersonalPresenter;
import com.zhihu.matisse.Matisse;

import java.util.List;

import androidx.annotation.Nullable;

@Route(path = AppRoute.USER_PERSONAL)
public class PersonalActivity extends BaseActivity<PersonalPresenter, PersonalContract.View> implements PersonalContract.View {

    private AvatarView avatarView;
    private TextView username, account, phone, company, department, systemRole, orgRole;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initContent() {
        findViewById(R.id.back_personal).setOnClickListener(getOnClickListener());
        avatarView = findViewById(R.id.user_avatar_personal);
        avatarView.setOnClickListener(getOnClickListener());
        username = findViewById(R.id.user_name_personal);
        username.setOnClickListener(getOnClickListener());
        account = findViewById(R.id.account_personal);
        phone = findViewById(R.id.phone_personal);
        company = findViewById(R.id.company_personal);
        department = findViewById(R.id.department_personal);
        systemRole = findViewById(R.id.system_role_personal);
        orgRole = findViewById(R.id.org_role_personal);
        FrameLayout phoneLy = findViewById(R.id.phone_ly_personal);
        phoneLy.setOnClickListener(getOnClickListener());
    }

    @Override
    public PersonalPresenter onCreatePresenter() {
        return new PersonalPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().start();
    }

    @Override
    protected boolean isStatusBarLight() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_personal) {
            finish();
        } else if (v.getId() == R.id.user_avatar_personal) {
            updateAvatar();
        } else if (v.getId() == R.id.user_name_personal) {
            updateUserName();
        } else if (v.getId() == R.id.phone_ly_personal) {
            updatePhone();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE.SELECT_PHOTO_CODE:
                if (resultCode != RESULT_OK || data == null){
                    return;
                }
                List<String> photoPaths = Matisse.obtainPathResult(data);
                if (photoPaths == null || photoPaths.size() < 1){
                    return;
                }
                AppImageLoader.displayAvatar(photoPaths.get(0), avatarView);
                getPresenter().updateAvatar(photoPaths.get(0));
                break;
            case REQUEST_CODE.UPDATE_USERNAME:
                if (resultCode != REQUEST_CODE.UPDATE_USERNAME || data == null){
                    return;
                }
                String userName = data.getStringExtra(IUserConstant.USER_NAME);
                if (username.getText().toString().equals(userName)){
                    return;
                }
                getPresenter().updateNickName(userName);
                break;
            default:
                break;
        }
    }

    @Override
    public void setupUser(User user) {
        AppImageLoader.displayAvatar(user.getAvatar(), user.getNickname(), avatarView);
        username.setText(user.getNickname());
        account.setText(user.getUsername());
        phone.setText(user.getPhone());
        if (user.getGroupList() != null && user.getGroupList().size() > 0) {
            department.setText(user.getGroupList().get(0).getGroupName());
        }
        if (user.getRoleList() != null && user.getRoleList().size() > 0) {
            orgRole.setText(user.getRoleList().get(0).getRoleName());
        }
    }

    @Override
    public void setupCompany(Organization org) {
        company.setText(getHomeOrgName(org));
    }

    private void updateAvatar() {
        AppPictureSelector.takePhotoSelector(this, 1, REQUEST_CODE.SELECT_PHOTO_CODE);
    }

    private void updateUserName() {
        Intent intent = new Intent(this, UserNameActivity.class);
        intent.putExtra(IUserConstant.USER_NAME, username.getText().toString());
        startActivityForResult(intent, REQUEST_CODE.UPDATE_USERNAME);
    }

    private void updatePhone() {

    }

    private String getHomeOrgName(Organization org) {
        for(OrganizationInfo info : org.getOrgList()){
            if (info.getId().equals(org.getHomeOrgId())){
                return info.getName();
            }
        }
        return null;
    }

    @Override
    public void finish() {
        setResult(REQUEST_CODE.CHANGE_PERSONAL);
        super.finish();
    }
}
