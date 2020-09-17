package com.duobang.user.contacts.imp;

import android.view.View;

import com.duobang.middleware.activity.DefaultActivity;
import com.duobang.middleware.constant.IUserConstant;
import com.duobang.middleware.contract.DefaultContract;
import com.duobang.middleware.contract.DefaultPresenter;
import com.duobang.pms_lib.common.DuobangLinearLayoutManager;
import com.duobang.pms_lib.utils.MessageUtils;
import com.duobang.user.R;
import com.duobang.user.contacts.adapter.UserAdapter;
import com.duobang.user.core.login.User;
import com.duobang.user.core.org.imp.OrganizationNetWork;
import com.duobang.user.i.org.IGroupUserListener;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GroupUserActivity extends DefaultActivity implements DefaultContract.View {

    private String groupId;
    private RecyclerView mRecyclerView;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_group_user;
    }

    @Override
    protected boolean handleIntent() {
        groupId = getIntent().getStringExtra(IUserConstant.GROUP_ID);
        return groupId != null;
    }

    @Override
    protected void initContent() {
        findViewById(R.id.back_group_user).setOnClickListener(getOnClickListener());
        mRecyclerView = findViewById(R.id.list_group_user);
    }

    @Override
    protected void initData() {
        super.initData();
        loadGroupUserList(groupId);
    }

    private void loadGroupUserList(String groupId) {
        OrganizationNetWork.getInstance().loadOrgGroupUsers(groupId, new IGroupUserListener() {
            @Override
            public void onGroupUserList(List<User> users) {
                setupRecyclerView(users);
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }

    private void setupRecyclerView(List<User> users) {
        UserAdapter adapter = new UserAdapter(users);
        mRecyclerView.setLayoutManager(new DuobangLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public DefaultPresenter onCreatePresenter() {
        return new DefaultPresenter();
    }

    @Override
    protected boolean isStatusBarLight() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_group_user){
            finish();
        }
    }
}
