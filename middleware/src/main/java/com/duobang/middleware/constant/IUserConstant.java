package com.duobang.middleware.constant;

public interface IUserConstant {

    String KEY = "user";
    /**
     * 验证码页面验证类型
     */
    interface INVITATION_TYPE {
        /* 激活账号 */ int ACCOUNT_ACTIVATE = 1;
        /* 手机号验证 */ int PHONE_VERIFY = 2;
    }

    /**
     * 用户状态
     */
    interface USER_STATE {
        /* 未激活 */ int NOT_ACTIVATION = 0;
        /* 可用 */ int AVAILABLE = 1;
        /* 禁用 */ int DISABLE = 2;
        /* 已删除 */ int DELETED = 3;
    }

    /**
     * 隐私声明网页地址
     */
    String CONTRACT_URL = "url";

    /**
     * 用户名
     */
    String USER_NAME = "username";

    /**
     * 部门ID
     */
    String GROUP_ID = "groupId";
    String GROUP_NAME = "groupName";

    /**
     * 组织ID
     */
    String ORG_ID = "orgId";

    /**
     * 组织名称
     */
    String ORG_NAME = "orgName";
    /**
     * 按组选择用户类型
     * {@link IWorkbenchConstant.APPROVAL_CONCRETE}
     */
    String CHOOSE_GROUP_USER_TYPE = "chooseGroupUserType";
}
