package com.android.architecture.framework;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.architecture.R;
import com.android.architecture.core.update.Update;
import com.android.architecture.framework.contract.MainContract;
import com.android.architecture.framework.presenter.MainPresenter;
import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.manager.DownloadManager;
import com.duobang.middleware.activity.BaseActivity;
import com.duobang.middleware.common.AppImageLoader;
import com.duobang.middleware.constant.REQUEST_CODE;
import com.duobang.middleware.router.AppRoute;
import com.duobang.middleware.weight.AvatarView;
import com.duobang.pms_lib.navigation.DuobangNavigation;
import com.duobang.pms_lib.navigation.constant.Anim;
import com.duobang.pms_lib.single_click.SingleClick;
import com.duobang.pms_lib.utils.MessageUtils;
import com.duobang.user.contacts.OrganizationFragment;
import com.duobang.user.personal.PersonalFragment;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import static com.duobang.pms_lib.navigation.DuobangNavigation.MODE_NORMAL;

@Route(path = AppRoute.MAIN_VIEW)
public class MainActivity extends BaseActivity<MainPresenter, MainContract.View> implements MainContract.View , OrganizationFragment.OnOrganizationSwitchListener {

    private DuobangNavigation navigation;
    private DrawerLayout drawer;
    private AvatarView userIcon;
    private TextView userNameTv, orgNameTv;
    private boolean mBackKeyPressed = false;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContent() {
        navigation = findViewById(R.id.navigation_main);
        drawer = findViewById(R.id.drawer_main);
        initDrawerView();
    }

    private void initDrawerView() {
        userIcon = findViewById(R.id.user_icon_drawer_main);
        userIcon.setOnClickListener(getOnClickListener());
        userNameTv = findViewById(R.id.user_name_drawer_main);
        userNameTv.setOnClickListener(getOnClickListener());
        orgNameTv = findViewById(R.id.org_name_drawer_main);
        orgNameTv.setOnClickListener(getOnClickListener());
        ImageView qrCode = findViewById(R.id.qrcode_drawer_main);
        qrCode.setOnClickListener(getOnClickListener());
        TextView switchOrg = findViewById(R.id.switch_org_drawer_main);
        switchOrg.setOnClickListener(getOnClickListener());
        TextView setting = findViewById(R.id.setting_drawer_main);
        setting.setOnClickListener(getOnClickListener());
        //TODO 隐藏无内容组件
        qrCode.setVisibility(View.INVISIBLE);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    protected void initData() {
        getPresenter().start();
    }

    @Override
    public MainPresenter onCreatePresenter() {
        return new MainPresenter();
    }

    @Override
    protected boolean isStatusBarLight() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon_drawer_main:
            case R.id.user_name_drawer_main:
            case R.id.org_name_drawer_main:
                openPersonalView();
                break;
            case R.id.qrcode_drawer_main:
                // TODO show qrcode dialog
                break;
            case R.id.switch_org_drawer_main:
                drawer.closeDrawer(GravityCompat.START);
                openSwitchOrgView();
                break;
            case R.id.setting_drawer_main:
                openSettingView();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE.CHANGE_PERSONAL:
                if (resultCode != REQUEST_CODE.CHANGE_PERSONAL){
                    return;
                }
                // TODO 暂时这样设置，接受回调，来修改"我的"头像、昵称 ，注意此处第五个Fragment，必须是PersonalFragment
                ((PersonalFragment) getNavigationBar().getFragmentList().get(4)).setupView();
                break;
            case REQUEST_CODE.SWITCH_ORG:
                if (resultCode != REQUEST_CODE.SWITCH_ORG){
                    return;
                }
                getPresenter().start();
                break;
            default:
                break;
        }
    }

    @Override
    public void startTaskService() {

    }

    @Override
    public void setupNavigation(String[] tabText, int[] normalIcon, int[] selectIcon, List<Fragment> fragments) {
        navigation.titleItems(tabText).normalIconItems(normalIcon).selectIconItems(selectIcon).fragmentList(fragments).fragmentManager(getSupportFragmentManager()).canScroll(true).mode(MODE_NORMAL).anim(Anim.BounceIn).build();
        navigation.getViewPager().setCurrentItem(0);
    }

    @Override
    public void setupDrawer(String name, String url, String orgName) {
        userNameTv.setText(name);
        AppImageLoader.displayAvatar(url, name, userIcon);
        orgNameTv.setText(orgName);
    }

    @Override
    public DuobangNavigation getNavigationBar() {
        return navigation;
    }

    @Override
    public DrawerLayout getDrawer() {
        return drawer;
    }

    @SingleClick
    @Override
    public void openPersonalView() {
        AppRoute.openPersonalView().navigation(this, REQUEST_CODE.CHANGE_PERSONAL);
    }

    @SingleClick
    @Override
    public void openSwitchOrgView() {
        AppRoute.openSwitchOrgView(this, REQUEST_CODE.SWITCH_ORG);
    }

    @SingleClick
    @Override
    public void openSettingView() {

    }

    @Override
    public void update(Update update){
        // TODO 进一步实现更新提示页、内部更新进度、断点更新、安装成功删除安装包、检测是否已下载未安装
        DownloadManager.getInstance(this)
                .setApkName("pms.apk")
                .setApkUrl(update.getInstallUrl())
                .setSmallIcon(R.drawable.ic_pms_logo)
                .setConfiguration(new UpdateConfiguration().setForcedUpgrade(true))
                .setApkVersionCode(update.getVersion())
                .setApkVersionName(update.getVersionShort())
                .setApkSize(update.getApkSize())
                .setApkDescription(update.getChangelog())
                .download();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!mBackKeyPressed) {
                MessageUtils.shortToast("再按一次退出");
                mBackKeyPressed = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mBackKeyPressed = false;
                    }
                }, 2000);
            } else {
                finish();
            }
        }
    }

    @Override
    public void onOrganizationSwitch() {
        getPresenter().start();
    }
}
