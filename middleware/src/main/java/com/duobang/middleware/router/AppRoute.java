package com.duobang.middleware.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.duobang.middleware.constant.IPmsConstant;
import com.duobang.middleware.constant.IUserConstant;
import com.duobang.middleware.constant.IWorkbenchConstant;
import com.duobang.middleware.environment.AppConfig;
import com.duobang.pms_lib.lifecycle.AppActivityLifecycle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * 路由
 * 跳转统一管理类（主要用户模块间跳转，以及同模块内通用型页面跳转）
 */
public class AppRoute {

    // activity生命周期回调统一处理
    private static final AppActivityLifecycle sAppActivityLifecycle = new AppActivityLifecycle();
    /**
     * 主页面
     */
    public static final String MAIN_VIEW = "/pms/main";
    /**
     * 登录
     */
    public static final String USER_LOGIN = "/user/login";
    /**
     * 隐私政策、服务协议
     */
    public static final String APP_CONTRACT = "/user/contract";
    /**
     * 填写邀请码
     */
    public static final String INVITATION_CODE = "/invita/invitation";
    /**
     * 消息
     */
    public static final String MAIN_COMMUNICATION = "/pms/communication";
    /**
     * 消息
     */
    public static final String MAIN_DAILYTASK = "/orgId/maindonetoday";
    /**
     * 总览
     */
    public static final String MAIN_DASHBOARD = "/pms/dashboard";
    /**
     * 工程
     */
    public static final String MAIN_PROJECT = "/pms/project";
    public static final String PMS_STRUCTURE = "/pms/structure";
    public static final String PMS_STRUCTURE_VIEW = "/pms/structureView";
    public static final String PMS_MODEL = "/pms/model";

    /**
     * 设置
     *
     * TODO 暂时将设置放到专业模块，有时间再搬移
     */
    public static final String SETTING = "/pms/setting";
    /**
     * 项目 -- 公司
     */
    public static final String MAIN_COMPANY = "/pms/company";
    /**
     * 工作台
     */
    public static final String MAIN_WORKBENCH = "/workbench/main";
    /**
     * 联系人
     */
    public static final String MAIN_CONTACT = "/user/contact";
    /**
     * 个人信息
     */
    public static final String USER_PERSONAL = "/user/personal";
    /**
     * 工作台应用对应router
     * 后台/前端/移动端应用管理协议，调整需要联系后台及前端
     */
    public static final String APP_WEALTH = "/orgId/wealth";
    public static final String APP_LABOR = "/orgId/labor";
    public static final String APP_MATERIAL = "/orgId/material";
    public static final String APP_WORKREPORT = "/orgId/workreport";
    public static final String APP_TASK = "/orgId/task";
    public static final String APP_CLOUD = "/orgId/cloud";
    public static final String APP_APPROVE = "/orgId/approve";
    public static final String APP_SCHEDULE = "/orgId/donetoday";
    public static final String APP_MEETING = "/orgId/meeting";
    public static final String APP_NOTE = "/orgId/note";
    public static final String APP_NOTICE = "/orgId/notice";
    /**
     * 选择用户
     */
    public static final String CHOOSE_USER = "/orgId/choose/user";
    /**
     * 分部门选择用户
     */
    public static final String CHOOSE_GROUP = "/orgId/choose/group";
    /**
     * 分部门选择用户
     */
    public static final String CHOOSE_GROUP_USER = "/orgId/choose/group/user";
    /**
     * 查询
     */
    public static final String PMS_SEARCH = "/pms/search";
    /**
     * 评论
     */
    public static final String COMMON_COMMENT = "/common/comment";
    /**
     * 统计片段
     */
    public static final String PMS_STAT = "/pms/stat";

    /**
     * 切换组织
     */
    public static final String SWITCH_ORG = "/user/switchOrg";

    /**
     * 我的（主页）
     */
    public static final String MAIN_PERSONAL = "/user/person";

    /**
     * 初始化
     */
    public static void init(@NonNull Application applicationContext, boolean debug) {
        if (debug) {
            debug();
        }
        ARouter.init(applicationContext);
        applicationContext.registerActivityLifecycleCallbacks(sAppActivityLifecycle);
    }

    private static void debug() {
        ARouter.openDebug();
        ARouter.openLog();
        ARouter.printStackTrace();
    }

    public static void route(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public static void route(Context context, String path) {
        ARouter.getInstance().build(path).navigation(context);
    }

    /**
     * 跳转主页面
     */
    public static void startMainView(Context context) {
        route(context, MAIN_VIEW);
        finish();
    }

    /**
     * 跳转主页面
     */
    public static void startMainViewFromFirst(Context context) {
        route(context, MAIN_VIEW);
    }

    /**
     * 打开登录页
     *
     * @param context 当前
     */
    public static void login(Context context) {
        route(context, USER_LOGIN);
        finish();
    }

    /**
     * 打开登录页
     *
     * @param context 当前
     */
    public static void loginFromFirst(Context context) {
        route(context, USER_LOGIN);
    }

    /**
     * 打开服务协议、隐私声明页
     */
    public static void openContractView(Context context) {
        ARouter.getInstance().build(APP_CONTRACT)
                .withString(IUserConstant.CONTRACT_URL, AppConfig.CONTRACT_URL)
                .navigation(context);
    }

    public static void openInvitationCodeView(Context context) {
        route(context, INVITATION_CODE);
        finish();
    }

    /**
     * 主页导航
     */
    public static Fragment newCommunicationFragment() {
        return (Fragment) ARouter.getInstance().build(MAIN_COMMUNICATION).navigation();
    }

    public static Fragment newDailyTaskMainFragment() {
        return (Fragment) ARouter.getInstance().build(MAIN_DAILYTASK).navigation();
    }

    public static Fragment newDashboardFragment() {
        return (Fragment) ARouter.getInstance().build(MAIN_DASHBOARD).navigation();
    }

    public static Fragment newCompanyFragment() {
        return (Fragment) ARouter.getInstance().build(MAIN_COMPANY).navigation();
    }

    public static Fragment newProjectFragment() {
        return (Fragment) ARouter.getInstance().build(MAIN_PROJECT).navigation();
    }

    public static Fragment newWorkbenchFragment() {
        return (Fragment) ARouter.getInstance().build(MAIN_WORKBENCH).navigation();
    }

    public static Fragment newContactFragment() {
        return (Fragment) ARouter.getInstance().build(MAIN_CONTACT).navigation();
    }

    public static Fragment newPersonalFragment() {
        return (Fragment) ARouter.getInstance().build(MAIN_PERSONAL).navigation();
    }

    /**
     * 打开个人信息页
     */
    public static Postcard openPersonalView() {
        return ARouter.getInstance().build(USER_PERSONAL);
    }

    /**
     * 打开设置页
     */
    public static void openSettingView() {
        route(SETTING);
    }

    /**
     * 打开单位工程页面
     */
    public static void openStructureView(Context context, String structureId, String structureName) {
        ARouter.getInstance().build(PMS_STRUCTURE)
                .withString(IPmsConstant.STRUCTURE.ID, structureId)
                .withString(IPmsConstant.STRUCTURE.NAME, structureName)
                .navigation(context);
    }

    /**
     * 打开单位工程详情
     */
    public static void openStructureViewView(Context context, String structureId) {
//        ARouter.getInstance().build(PMS_STRUCTURE_VIEW)
//                .withString(IPmsConstant.STRUCTURE.ID, structureId)
//                .navigation(context);
    }

    /**
     * 打开模型页
     */
    public static void openModelView(Context context, String structureId, String modelId) {
        ARouter.getInstance().build(PMS_MODEL)
                .withString(IPmsConstant.STRUCTURE.ID, structureId)
                .withString(IPmsConstant.MODEL.ID, modelId)
                .navigation(context);
    }

    /**
     * 打开查询页面
     * 共用同一个Activity，根据type，配置不同的查询路径
     *
     * @param context 上下文对象
     * @param type 查询类型
     */
    public static void openSearchView(Context context, int type) {
        ARouter.getInstance().build(PMS_SEARCH)
                .withInt(IPmsConstant.SEARCH_TYPE.KEY, type)
                .navigation(context);
    }

    /**
     * 打开评论页（图文）
     *
     * @param activity
     * @param REQUEST_CODE
     */
    public static void openCommentView(Activity activity, int REQUEST_CODE) {
        ARouter.getInstance().build(COMMON_COMMENT).navigation(activity, REQUEST_CODE);
    }

    /**
     * 新建统计页面
     *
     * @param structureId
     * @param statType
     * @return
     */
    public static Fragment newStatFragment(String structureId, String statType) {
        return (Fragment) ARouter.getInstance().build(PMS_STAT)
                .withString(IPmsConstant.STRUCTURE.ID, structureId)
                .withString(IPmsConstant.STAT_DIM.KEY, statType)
                .navigation();
    }

    /**
     * 打开切换组织页
     *
     * @param activity
     * @param REQUEST_CODE
     */
    public static void openSwitchOrgView(Activity activity, int REQUEST_CODE) {
        ARouter.getInstance().build(SWITCH_ORG).navigation(activity, REQUEST_CODE);
    }

    public static void openNoticeView(){
        route(APP_NOTICE);
    }

    public static void openNoteView(){
        route(APP_NOTE);
    }

    public static void openTaskView(){
        route(APP_TASK);
    }

    public static void openApprovalView(){
        route(APP_APPROVE);
    }

    public static void openReportView(){
        route(APP_WORKREPORT);
    }

    /**
     * 打开单选用户页
     */
    public static void openChooseUserView(Activity activity, int requestCode, boolean isSingle, String json) {
        ARouter.getInstance().build(CHOOSE_USER)
                .withBoolean(IWorkbenchConstant.USER.IS_SINGLE, isSingle)
                .withString(IWorkbenchConstant.USER.CHOOSE_LIST, json)
                .withInt(IWorkbenchConstant.USER.REQUSET_CODE, requestCode)
                .navigation(activity, requestCode);
    }

    /**
     * 打开部门选择页
     */
    public static void openChooseGroupView(int chooseType) {
        ARouter.getInstance().build(CHOOSE_GROUP)
                .withInt(IWorkbenchConstant.USER.CHOOSE_GROUP_USER_TYPE, chooseType)
                .navigation();
    }
    /**
     * 打开部门用户页
     */
    public static void openChooseGroupUserView(int chooseType, String groupId, String groupName, String json) {
        ARouter.getInstance().build(CHOOSE_GROUP_USER)
                .withInt(IWorkbenchConstant.USER.CHOOSE_GROUP_USER_TYPE, chooseType)
                .withString(IWorkbenchConstant.USER.GROUP_ID, groupId)
                .withString(IWorkbenchConstant.USER.GROUP_NAME, groupName)
                .withString(IWorkbenchConstant.USER.CHOOSE_LIST, json)
                .navigation();
    }

    /**
     * 关闭所有activity
     */
    public static void finish() {
        sAppActivityLifecycle.finish();
    }
}
