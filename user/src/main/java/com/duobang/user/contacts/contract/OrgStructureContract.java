package com.duobang.user.contacts.contract;

import com.duobang.pms_lib.i.framework.IBaseView;
import com.duobang.user.core.login.User;
import com.duobang.user.core.org.OrgGroup;

import java.util.List;

public interface OrgStructureContract {

    interface Presenter{

        /**
         * 获取组织、部分、用户
         *
         * {@link com.duobang.user.core.org.OrgWrapper}
         */
        void loadOrgUserGroup();
    }

    interface View extends IBaseView {

        /**
         * 渲染部分列表
         *
         * @param groups
         */
        void setupGroupView(List<OrgGroup> groups);

        /**
         * 渲染用户列表
         *
         * @param users
         */
        void setupUserView(List<User> users);

        /**
         * 获取组织ID
         *
         * @return
         */
        String getOrgId();
    }
}
