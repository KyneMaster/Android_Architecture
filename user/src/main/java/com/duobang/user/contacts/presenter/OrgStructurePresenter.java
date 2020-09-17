package com.duobang.user.contacts.presenter;

import com.duobang.pms_lib.framework.BasePresenter;
import com.duobang.pms_lib.utils.MessageUtils;
import com.duobang.user.contacts.contract.OrgStructureContract;
import com.duobang.user.core.org.OrgWrapper;
import com.duobang.user.core.org.imp.OrganizationNetWork;
import com.duobang.user.i.org.IOrgWrapperListener;

public class OrgStructurePresenter extends BasePresenter<OrgStructureContract.View> implements OrgStructureContract.Presenter{
    @Override
    protected void onStart() {
        checkViewAttached();
        loadOrgUserGroup();
    }

    @Override
    public void loadOrgUserGroup() {
        String orgId = getView().getOrgId();
        OrganizationNetWork.getInstance().loadOrgGroupUserWrapper(orgId, false, new IOrgWrapperListener() {
            @Override
            public void onOrgGroupUserWrapper(OrgWrapper wrapper) {
                getView().setupGroupView(wrapper.getGroupList());
                getView().setupUserView(wrapper.getUserList());
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }
}
