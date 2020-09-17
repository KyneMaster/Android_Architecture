package com.android.architecture.framework.presenter;

import com.android.architecture.R;
import com.android.architecture.core.update.Update;
import com.android.architecture.core.update.imp.UpdateNetWork;
import com.android.architecture.framework.contract.MainContract;
import com.android.architecture.i.update.IUpdateListener;
import com.duobang.middleware.cache.PreferenceManager;
import com.duobang.middleware.constant.IPmsConstant;
import com.duobang.middleware.environment.AppConfig;
import com.duobang.middleware.router.AppRoute;
import com.duobang.pms_lib.framework.BasePresenter;
import com.duobang.pms_lib.utils.LoadingUtils;
import com.duobang.pms_lib.utils.MessageUtils;
import com.duobang.user.core.org.Organization;
import com.duobang.user.core.org.OrganizationInfo;
import com.duobang.user.core.org.imp.OrganizationNetWork;
import com.duobang.user.i.org.IPersonOrgListener;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * 主页面两种情况
 * A: 进入公司层，展示项目
 * B: 直接进入到项目中，展示工程
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private String[] tabText_A = {"总览", "项目", "日事日毕", "联系人", "我"};
    private String[] tabText_B = {"总览", "工程", "日事日毕", "联系人", "我"};
    private int[] normalIcon = {R.drawable.ic_overview_normal, R.drawable.ic_project_normal, R.drawable.ic_dailytask_normal, R.drawable.ic_user_normal, R.drawable.ic_person_nomal};
    private int[] selectIcon = {R.drawable.ic_overview_select, R.drawable.ic_project_select, R.drawable.ic_dailytask_select, R.drawable.ic_user_select, R.drawable.ic_person_select};
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onStart() {
        checkViewAttached();
        checkUpdate();
        loadUserOrg();
    }

    @Override
    public void checkUpdate() {
        UpdateNetWork.getInstance().loadAppInfo(AppConfig.UPDATE_APP_KEY, AppConfig.UPDATE_APP_TOKEN, new IUpdateListener() {
            @Override
            public void onUpdateSuccess(Update update) {
                getView().update(update);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @Override
    public void loadUserOrg() {
        LoadingUtils.showLoadingDialog(getView().getContext());
        OrganizationNetWork.getInstance().loadPersonOrg(new IPersonOrgListener() {
            @Override
            public void onLoadPersonOrg(Organization org) {
                saveOrgInfo(org.getHomeOrgInfo());
                LoadingUtils.dismissLoadingDialog();
                setupNavigation();
                setupDrawer();
                getView().startTaskService();
            }

            @Override
            public void onFailure(String message) {
                LoadingUtils.dismissLoadingDialog();
                MessageUtils.shortToast(message);
                AppRoute.openInvitationCodeView(getView().getContext());
            }
        });
    }

    @Override
    public void setupNavigation() {
        if (PreferenceManager.getInstance().getUserPreferences().getUserOrgType() == IPmsConstant.ORG_TYPE.COMPANY) {
            fragments = getAFragments();
            getView().setupNavigation(tabText_A, normalIcon, selectIcon, fragments);
        } else {
            fragments = getBFragments();
            getView().setupNavigation(tabText_B, normalIcon, selectIcon, fragments);
        }
    }

    @Override
    public void setupDrawer() {
        String userName = PreferenceManager.getInstance().getUserPreferences().getNickName();
        String userIcon = PreferenceManager.getInstance().getUserPreferences().getUserAvatar();
        String orgName = PreferenceManager.getInstance().getUserPreferences().getUserOrgName();
        getView().setupDrawer(userName, userIcon, orgName);
    }

    private void saveOrgInfo(OrganizationInfo info) {
        if (info == null) {
            return;
        }
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgId(info.getId());
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgName(info.getName());
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgState(info.getState());
        PreferenceManager.getInstance().getUserPreferences().saveUserOrgType(info.getType());
    }

    private List<Fragment> getAFragments() {
        fragments = new ArrayList<>();
        fragments.add(AppRoute.newDashboardFragment());
        fragments.add(AppRoute.newCompanyFragment());
        fragments.add(AppRoute.newDailyTaskMainFragment());
        fragments.add(AppRoute.newContactFragment());
        fragments.add(AppRoute.newPersonalFragment());
        return fragments;
    }

    private List<Fragment> getBFragments() {
        fragments = new ArrayList<>();
        fragments.add(AppRoute.newDashboardFragment());
        fragments.add(AppRoute.newProjectFragment());
        fragments.add(AppRoute.newDailyTaskMainFragment());
        fragments.add(AppRoute.newContactFragment());
        fragments.add(AppRoute.newPersonalFragment());
        return fragments;
    }
}
