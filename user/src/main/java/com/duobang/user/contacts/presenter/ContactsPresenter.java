package com.duobang.user.contacts.presenter;

import com.duobang.pms_lib.framework.BasePresenter;
import com.duobang.pms_lib.utils.MessageUtils;
import com.duobang.user.contacts.contract.ContactsContract;
import com.duobang.user.core.org.Organization;
import com.duobang.user.core.org.imp.OrganizationNetWork;
import com.duobang.user.i.org.IPersonOrgListener;

public class ContactsPresenter extends BasePresenter<ContactsContract.View> implements ContactsContract.Presenter {
    @Override
    protected void onStart() {
        loadOrganizationList();
    }

    @Override
    public void loadOrganizationList() {
        OrganizationNetWork.getInstance().loadPersonOrg(new IPersonOrgListener() {
            @Override
            public void onLoadPersonOrg(Organization org) {
                org.setHomeOrg();
                getView().setupOrganizationView(org.getOrgList());
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
            }
        });
    }
}
