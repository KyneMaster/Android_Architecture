package com.duobang.user.contacts.imp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.duobang.middleware.activity.BaseActivity;
import com.duobang.middleware.constant.IUserConstant;
import com.duobang.pms_lib.adapter.BaseLibAdapter;
import com.duobang.pms_lib.common.DuobangLinearLayoutManager;
import com.duobang.user.R;
import com.duobang.user.contacts.adapter.OrgGroupAdapter;
import com.duobang.user.contacts.adapter.UserAdapter;
import com.duobang.user.contacts.contract.OrgStructureContract;
import com.duobang.user.contacts.presenter.OrgStructurePresenter;
import com.duobang.user.core.login.User;
import com.duobang.user.core.org.OrgGroup;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrgStructureActivity extends BaseActivity<OrgStructurePresenter, OrgStructureContract.View> implements OrgStructureContract.View{

    private RecyclerView groupView;
    private RecyclerView userView;
    private String orgId;
    private String orgName;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_org_structure;
    }

    @Override
    protected boolean handleIntent() {
        orgId = getIntent().getStringExtra(IUserConstant.ORG_ID);
        orgName = getIntent().getStringExtra(IUserConstant.ORG_NAME);
        return orgId != null;
    }

    @Override
    protected void initContent() {
        TextView title = findViewById(R.id.back_org_structure);
        title.setOnClickListener(getOnClickListener());
        findViewById(R.id.search_org_structure).setOnClickListener(getOnClickListener());
        groupView = findViewById(R.id.group_list_org_structure);
        userView = findViewById(R.id.list_org_structure);
        //TODO 隐藏无内容组件
        findViewById(R.id.search_org_structure).setVisibility(View.INVISIBLE);
        title.setText(orgName);
    }

    @Override
    public OrgStructurePresenter onCreatePresenter() {
        return new OrgStructurePresenter();
    }

    @Override
    protected void initData() {
        super.initData();
        getPresenter().start();
    }

    @Override
    protected boolean isStatusBarLight() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_org_structure){
            finish();
        } else if (v.getId() == R.id.search_org_structure){

        }
    }

    @Override
    public void setupGroupView(List<OrgGroup> groups) {
        OrgGroupAdapter groupAdapter = new OrgGroupAdapter(groups);
        groupView.setLayoutManager(new DuobangLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        groupView.setAdapter(groupAdapter);
        groupAdapter.setOnItemClickListener(new BaseLibAdapter.onItemClickListener<OrgGroup>() {
            @Override
            public void onItemClick(Context context, int i, OrgGroup item) {
                openGroupUserView(item.getId());
            }
        });
    }

    @Override
    public void setupUserView(List<User> users) {
        UserAdapter userAdapter = new UserAdapter(users);
        userView.setLayoutManager(new DuobangLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        userView.setAdapter(userAdapter);
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    private void openGroupUserView(String groupId) {
        Intent intent = new Intent(this, GroupUserActivity.class);
        intent.putExtra(IUserConstant.GROUP_ID, groupId);
        startActivity(intent);
    }
}
