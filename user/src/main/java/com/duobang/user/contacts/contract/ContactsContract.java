package com.duobang.user.contacts.contract;

import com.duobang.pms_lib.i.framework.IBaseView;
import com.duobang.user.core.org.OrganizationInfo;

import java.util.List;

public interface ContactsContract {

    interface Presenter {

        void loadOrganizationList();
    }

    interface View extends IBaseView {

        void setupOrganizationView(List<OrganizationInfo> organizations);
    }
}
