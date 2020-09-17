package com.duobang.middleware.constant;

public interface IWorkbenchConstant {

    /**
     * 重新加载
     */
    int RELOAD_TYPE = 0;
    /**
     * 继续加载
     */
    int ADDLOAD_TYPE = 1;

    /**
     * app类型
     */
    interface APP_TYPE {
        /* 系统级应用 */ int SYSTEM = 0;
        /* 专业应用 */ int MAJOR = 1;
        /* 通用应用 */ int CURRENCY = 2;
    }

    /**
     * 日事日毕
     */
    interface SCHEDULE {
        /* 事项ID */ String SCHEDULE_ID = "scheduleId";
        /* 操作类型/创建：修改 */ String OPERATE_TYPE = "operateType";
        /* 创建操作 */ int CREATE = 1;
        /* 修改操作 */ int UPDATE = 2;
    }

    /**
     * 通用记录
     */
    interface NOTE {
        /* 重新加载 */ int RELOAD_TYPE = 0;
        /* 继续加载 */ int ADDLOAD_TYPE = 1;
        /* 列表改变 */ String CHANGE_NOTE = "changeNote";
        /* 创建人ID */ String CREATOR_ID = "creatorId";
    }

    /**
     * 例会
     */
    interface MEETING {
        String KEY_ID = "meetingId";
    }

    /**
     * 用户列表，单选/多选
     */
    interface USER {
        String CHOOSE_USER = "chooseUser";
        String IS_SINGLE = "isSingle";
        String CHOOSE_LIST = "chooseList";
        String REQUSET_CODE = "requestCode";
        String CHOOSE_GROUP_USER_TYPE = "chooseGroupUserType";
        String GROUP_ID = "groupId";
        String GROUP_NAME = "groupName";
    }

    interface APPROVAL {
        String TYPE = "approvalType";
        /* 费用报销 */String EXPENSE = "Expense";
        /* 物品领用 */String ITEM = "Item";
        /* 请假 */String LEAVE = "Leave";
        /* 出差 */String TRAVEL = "Travel";
        /* 混凝土 */String CONCRETE = "Concrete";
        int APPROVER = 0;
        int CC = 1;
        int GROUP = 2;
        String APPROVALID = "approvalId";
    }

    /**
     * 混凝土申请
     */
    interface APPROVAL_CONCRETE {
        /* 表单审批人 */int FORM_APPROVERS = 1;
        /* 生产发起人 */int PRODUCTION_APPLICANT = 2;
        /* 生产审批人 */int PRODUCTION_APPROVERS = 3;
        /* 发料执行人 */int OPERATOR = 4;
    }

    /**
     * 审批状态
     */
    interface APPROVAL_STATE{
        /* 撤回 */int REVOKE = 1;
        /* 通过 */int PASS = 2;
        /* 审批未通过 */int REFUSE = 3;
    }

    interface APPROVAL_SEND_CONCRETE_STATE{
        /* 运输中 */int IN_TRANSIT = 0;
        /* 已签收 */int RECEIVE = 1;
        /* 已退回 */int BACK = 2;
    }

    /**
     * 审批节点状态
     */
    interface APPROVAL_NODE_STATE{
        /* 等待中 */int WAIT = 0;
        /* 通过 */int PASS = 1;
        /* 拒绝 */int REFUSE = 2;
    }

    /**
     * 混凝土三个阶段状态 processState
     */
    interface APPROVAL_CONCRETE_STATE{
        /* 等待中(待处理) */int PENDING = 0;
        /* 审批中 */int UNDER_APPROVAL = 1;
        /* 同意 */int AGREED = 2;
        /* 拒绝 */int REFUSED = 3;
        /* 进行中(发料) */int ONGOING = 4;
        /* 完成(发料) */int FINISHED = 5;
    }

    /**
     * 节点类型
     */
    interface APPROVAL_NODE_TYPE{
        /* 发起人 */int CRETOR = 0;
        /* 审批人 */int APPROVER = 1;
        /* 抄送人 */int CC = 2;
    }
    /**
     * 节点类型
     * @link Approval.processeOrder
     */
    interface APPROVAL_CONCRETE_NODE_ORDER{
        /* 申请表单 */int FORM = 0;
        /* 生产计划 */int PRODUCTION_PLAN = 1;
        /* 发料 */int SEND_CONCRETE = 2;
    }

    /**
     * 审批操作
     */
    interface APPROVAL_OPERATE {
        /* 第几个节点 */ String POSITION = "position";
        /* 节点对象json */ String NODE = "node";
        /* 状态 */ String STATE = "state";
    }

    interface NOTICE{
        String KEY = "notice";
        /* 列表改变 */ String CHANGE_NOTICE = "changeNotice";
    }

    interface TASK{
        String ALLOW_STATE = "allowState";
        /* 公开 */int PUBLIC = 0;
        /* 私密 */int PRIVARE = 1;
        /* 部分可见 */int PART = 2;
    }

}
