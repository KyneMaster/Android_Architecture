package com.duobang.user.contacts;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.duobang.middleware.cache.PreferenceManager;
import com.duobang.middleware.constant.IUserConstant;
import com.duobang.middleware.fragment.BaseFragment;
import com.duobang.middleware.router.AppRoute;
import com.duobang.pms_lib.adapter.BaseLibAdapter;
import com.duobang.pms_lib.common.DuobangLinearLayoutManager;
import com.duobang.pms_lib.utils.MessageUtils;
import com.duobang.user.R;
import com.duobang.user.contacts.adapter.OrganizationAdapter;
import com.duobang.user.contacts.contract.ContactsContract;
import com.duobang.user.contacts.imp.OrgStructureActivity;
import com.duobang.user.contacts.presenter.ContactsPresenter;
import com.duobang.user.core.org.OrganizationInfo;
import com.duobang.user.core.org.imp.OrganizationNetWork;
import com.duobang.user.i.org.IOrgInfoListener;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

@Route(path = AppRoute.MAIN_CONTACT)
public class OrganizationFragment extends BaseFragment<ContactsPresenter, ContactsContract.View> implements ContactsContract.View{

    private RecyclerView mRecyclerView;
    private OrganizationAdapter adapter;
    private MaterialButton switchBtn;
    private OnOrganizationSwitchListener listener;

    private boolean isEdit = false;

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_organization;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.list_organization);
        switchBtn = findViewById(R.id.switch_organization);
        switchBtn.setOnClickListener(getOnClickListener());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment parent = getParentFragment();
        if (parent != null){
            listener = (OnOrganizationSwitchListener) parent;
        } else {
            listener = (OnOrganizationSwitchListener) context;
        }
    }

    @Override
    public ContactsPresenter onCreatePresenter() {
        return new ContactsPresenter();
    }

    @Override
    protected void onStartLoadData() {
        getPresenter().start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.switch_organization){
            if (isEdit){
                switchBtn.setText("切换组织");
                handleListData(false);
            } else {
                switchBtn.setText("关闭切换");
                handleListData(true);
            }
            isEdit = !isEdit;
        }
    }

    @Override
    public void setupOrganizationView(List<OrganizationInfo> organizations) {
        if (adapter == null) {
            adapter = new OrganizationAdapter(organizations);
            mRecyclerView.setLayoutManager(new DuobangLinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(adapter);
        }else {
            adapter.invalidate(organizations);
        }
        adapter.setOnItemClickListener(new BaseLibAdapter.onItemClickListener<OrganizationInfo>() {
            @Override
            public void onItemClick(Context context, int i, OrganizationInfo item) {
                if (item.isEdit()){
                    // TODO 刷新并上传主组织，刷新主页
                    switchHomeOrg(item);
                } else {
                    openOrgStructureView(item);
                }
            }
        });
    }

    /**
     * 跳转打开人员列表
     *
     * @param item
     */
    private void openOrgStructureView(OrganizationInfo item) {
        Intent intent = new Intent(getActivity(), OrgStructureActivity.class);
        intent.putExtra(IUserConstant.ORG_ID, item.getId());
        intent.putExtra(IUserConstant.ORG_NAME, item.getName());
        startActivity(intent);
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
                listener.onOrganizationSwitch();
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }

    /**
     * 缓存组织信息
     *
     * @param info
     */
    private void saveOrgInfo(OrganizationInfo info) {
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgId(info.getId());
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgName(info.getId());
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgState(info.getState());
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgType(info.getType());
    }

    /**
     * 设置列表编辑状态
     *
     * @param isEdit
     */
    private void handleListData(boolean isEdit) {
        if (adapter == null || adapter.getDataList().size() == 0){
            return;
        }
        List<OrganizationInfo> dataList = adapter.getDataList();
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).setEdit(isEdit);
        }
        adapter.invalidate(dataList);
    }

    public interface OnOrganizationSwitchListener{
        void onOrganizationSwitch();
    }
}
