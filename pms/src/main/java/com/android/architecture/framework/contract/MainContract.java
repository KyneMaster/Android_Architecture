package com.android.architecture.framework.contract;

import com.android.architecture.core.update.Update;
import com.duobang.pms_lib.i.framework.IBaseView;
import com.duobang.pms_lib.navigation.DuobangNavigation;
import com.duobang.pms_lib.single_click.SingleClick;

import java.util.List;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public interface MainContract {

    interface Presenter{
        /**
         * 检查更新
         */
        void checkUpdate();

        /**
         * 获取用户的组织，验证
         */
        void loadUserOrg();

        /**
         * 配置底部导航
         */
        void setupNavigation();
        /**
         * 配置抽屉
         */
        void setupDrawer();

    }
    interface View extends IBaseView {

        /**
         * 检测更新，若需要更新，展示更新dialog
         *
         * @param update
         */
        void update(Update update);

        /**
         * 启动任务服务
         */
        void startTaskService();

        /**
         * 配置主页面导航
         */
        void setupNavigation(String[] tabText, int[] normalIcon, int[] selectIcon, List<Fragment> fragments);

        /**
         * 设置drawer内容
         * @param name 用户名
         * @param url 头像URL
         * @param orgName 组织名
         */
        void setupDrawer(String name, String url, String orgName);

        /**
         * 获取主页面导航bar
         * @return DuobangNavigation
         */
        DuobangNavigation getNavigationBar();

        /**
         * 获取主页面drawer
         * @return DrawerLayout
         */
        DrawerLayout getDrawer();

        /**
         * 打开个人页面
         */
        @SingleClick
        void openPersonalView();

        /**
         * 打开切换组织页面
         */
        @SingleClick
        void openSwitchOrgView();

        /**
         * 打开设置页
         */
        @SingleClick
        void openSettingView();

    }
}
