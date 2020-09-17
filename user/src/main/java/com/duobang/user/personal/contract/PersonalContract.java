package com.duobang.user.personal.contract;

import com.duobang.pms_lib.i.framework.IBaseView;
import com.duobang.user.core.login.User;
import com.duobang.user.core.org.Organization;

public interface PersonalContract {

    interface Presenter {

        /**
         * 加载用户信息
         */
        void getPersonalInfo();

        /**
         * 加载用户所在组织信息
         */
        void getOrganizationInfo();

        /**
         * 修改头像
         *
         * @param avatarPath 图片本地路径
         */
        void updateAvatar(String avatarPath);

        /**
         * 修改昵称
         *
         * @param nickName 修改后的昵称
         */
        void updateNickName(String nickName);

        //        void updateUserName();

        //        void updatePhone();
    }

    interface View extends IBaseView {

        /**
         * 渲染用户信息
         *
         * @param user 用户信息
         */
        void setupUser(User user);

        /**
         * 渲染公司
         *
         * @param org 组织信息
         */
        void setupCompany(Organization org);
    }
}
