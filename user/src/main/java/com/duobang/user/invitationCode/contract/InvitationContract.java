package com.duobang.user.invitationCode.contract;

import com.duobang.pms_lib.i.framework.IBaseView;

public interface InvitationContract {

    interface Presenter{

        /**
         * 加入组织
         */
        void joinOrg();
    }

    interface View extends IBaseView{

        /**
         * 获取邀请码
         */
        String getInvitationCode();

        /**
         * 加入成功，进入主页面
         */
        void startMainView();
    }
}
