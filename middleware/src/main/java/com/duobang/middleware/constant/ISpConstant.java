package com.duobang.middleware.constant;

public interface ISpConstant {

    /**
     * 用户配置 UserPreferences
     */
    interface USER {

        String USER_ID = "userId";

        String USER_NAME = "username";

        String USER_NICK_NAME = "nickname";

        String USER_PHONE = "userPhone";

        String USER_AVATAR = "userAvatar";

        /**
         * 0未激活
         * 1可用
         * 2禁用
         * 3已删除
         * <p>
         * {@link IUserConstant.USER_STATE}
         */
        String USER_STATE = "state";
        String USER_ORG_ID = "userOrgId";
        String USER_ORG_NAME = "userOrgName";
        /**
         * 0: 公司 -> 项目
         * 1: 项目 -> 工程
         */
        String USER_ORG_TYPE = "userOrgType";

        /**
         * 0可用
         * 1已归档
         * 2禁用
         * 3已删除
         * <p>
         * {@link IPmsConstant.ORG_STATE}
         */
        String USER_ORG_STATE = "userOrgState";
    }

    /**
     * 成员缓存
     */
    interface MEMBER {

        String LIST = "members";

    }

    interface APP {
        /**
         * 任务 - 执行人 保存ID
         */
        String TASK_OPERATOR = "taskOerator";
    }

    /**
     * 汇报对象
     */
    interface REPORT {

        String LIST = "reportUsers";
    }
}
