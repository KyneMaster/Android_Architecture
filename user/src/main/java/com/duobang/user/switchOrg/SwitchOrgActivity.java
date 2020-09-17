package com.duobang.user.switchOrg;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duobang.middleware.activity.DefaultActivity;
import com.duobang.middleware.cache.PreferenceManager;
import com.duobang.middleware.constant.REQUEST_CODE;
import com.duobang.middleware.contract.DefaultPresenter;
import com.duobang.middleware.router.AppRoute;
import com.duobang.pms_lib.adapter.BaseLibAdapter;
import com.duobang.pms_lib.common.DuobangLinearLayoutManager;
import com.duobang.pms_lib.utils.MessageUtils;
import com.duobang.user.R;
import com.duobang.user.core.org.Organization;
import com.duobang.user.core.org.OrganizationInfo;
import com.duobang.user.core.org.imp.OrganizationNetWork;
import com.duobang.user.i.org.IOrgInfoListener;
import com.duobang.user.i.org.IPersonOrgListener;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@Route(path = AppRoute.SWITCH_ORG)
public class SwitchOrgActivity extends DefaultActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_switch_org;
    }

    @Override
    protected void initContent() {
        findViewById(R.id.back_switch_org).setOnClickListener(getOnClickListener());
        mRecyclerView = findViewById(R.id.list_switch_org);
    }

    @Override
    protected void initData() {
        OrganizationNetWork.getInstance().loadPersonOrg(new IPersonOrgListener() {
            @Override
            public void onLoadPersonOrg(Organization org) {
                org.setHomeOrg();
                setupRecyclerView(org.getOrgList());
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }

    private void setupRecyclerView(final List<OrganizationInfo> orgList) {
        final SwitchOrgAdapter adapter = new SwitchOrgAdapter(orgList);
        mRecyclerView.setLayoutManager(new DuobangLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseLibAdapter.onItemClickListener<OrganizationInfo>() {
            @Override
            public void onItemClick(Context context, int i, OrganizationInfo item) {
                for (int j = 0; j < orgList.size(); j++) {
                    if (orgList.get(j).getId().equals(item.getId())){
                        orgList.get(j).setCheck(true);
                    } else {
                        orgList.get(j).setCheck(false);
                    }
                }
                adapter.invalidate(orgList);
                switchHomeOrg(item);
            }
        });
    }

    /**
     * 切换主组织
     *
     * @param item
     */
    private void switchHomeOrg(OrganizationInfo item) {
        OrganizationNetWork.getInstance().switchHomeOrg(item.getId(), new IOrgInfoListener() {
            @Override
            public void onLoadOrgInfo(OrganizationInfo info) {
                MessageUtils.shortToast("切换成功！");
                saveOrgInfo(info);
                finish();
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }

    private void saveOrgInfo(OrganizationInfo info) {
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgId(info.getId());
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgName(info.getId());
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgState(info.getState());
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgType(info.getType());
    }

    @Override
    public DefaultPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected boolean isStatusBarLight() {
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_switch_org){
            finish();
        }
    }

    @Override
    public void finish() {
        setResult(REQUEST_CODE.SWITCH_ORG);
        super.finish();
    }
}
