package com.duobang.pms_lib.framework;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.duobang.pms_lib.R;
import com.duobang.pms_lib.i.framework.IBaseView;
import com.duobang.pms_lib.i.framework.IPresenter;
import com.duobang.pms_lib.immersionBar.ImmersionBar;
import com.duobang.pms_lib.utils.AndroidWorkaround;
import com.duobang.pms_lib.utils.IMEUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

public abstract class BaseLibActivity<P extends IPresenter<V>, V extends IBaseView> extends AppCompatActivity implements View.OnClickListener {

    private final List<WeakReference<BaseLibViewGroup>> childViewGroups = new ArrayList<>();
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = onCreatePresenter();
        init();
    }

    private void init() {
        if (handleIntent()) {
            attachPresenter();
            initView();
            initData();
        } else {
            finish();
        }
    }

    protected void attachPresenter() {
        if (getPresenter() != null) {
            getPresenter().attachView((V) this);
        }
    }

    public P getPresenter() {
        return presenter;
    }

    public Context getActivity() {
        return this;
    }

    public Context getContext() {
        return this;
    }

    /**
     * step1 初始化view
     *
     * @see #getContentViewResId()
     * @see #initContent()
     */
    private void initView() {
        int contentViewId = getContentViewResId();
        if (contentViewId != 0) {
            initStatusBar();
            doFullScreen();
            setContentView(contentViewId);
        } else {
            throw new RuntimeException("content view not found");
        }
        initToolbar();
        initContent();
    }

    /**
     * step1.1 返回content view res Id，如果content view完全从xml加载，推荐使用
     *
     * @return content view Id 如果需要代码添加布局的方式初始化请返回 0
     * @see #initView()
     */
    protected abstract int getContentViewResId();

    /**
     * 从Intent中获取数据加载数据
     * <p/>
     * return false如果入参发生问题，需要关闭本页面
     */
    protected boolean handleIntent() {
        return true;
    }

    /**
     * step1.2 初始化content
     *
     * @see #getOnClickListener()
     */
    protected abstract void initContent();

    /**
     * step2 从本地或网络加载数据
     */
    protected void initData() {

    }

    /**
     * 实例化presenter
     */
    public abstract P onCreatePresenter();

    /**
     * 推荐使用默认的view OnClickListener：没有特别原因，最好不要每次都new一个新的OnClickListener
     *
     * @return OnClickListener
     */
    protected View.OnClickListener getOnClickListener() {
        return this;
    }

    /**
     * toolbar相关实例化
     */
    protected void initToolbar() {

    }

    /**
     * 全屏
     */
    public void doFullScreen() {

    }

    /**
     * 适配底部虚拟导航按键
     *
     * @deprecated 已弃用， 使用ImmersionBar
     */
    private void initNavigationBar() {
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
    }

    /**
     * statusBar相关实例化
     *
     * {@link #isStatusBarLight()}
     */
    protected void initStatusBar() {
        ImmersionBar.with(this).navigationBarColor(R.color.black).statusBarDarkFont(isStatusBarLight()).init();
    }

    /**
     * Android 6.0 以上设置状态栏颜色
     *
     * @deprecated  已弃用， 使用ImmersionBar
     */
    protected void setStatusBar(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
            if (isLightColor(color)) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 沉浸式开发，Theme设置状态栏透明，此处对字体对应设置黑白色
     *
     * @deprecated 现在使用ImmersionBar库
     */
    private void setStatusBarTextColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isStatusBarLight()) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 状态栏是否为亮色
     *
     * @return boolean
     */
    protected abstract boolean isStatusBarLight();

    /**
     * 判断颜色是不是亮色
     *
     * @param color 状态栏颜色
     * @return boolean
     */
    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 获取StatusBar颜色，默认白色
     *
     * @return 主题色
     */
    protected @ColorInt
    int getStatusBarColor() {
        return Color.WHITE;
    }

    public void addChildView(BaseLibViewGroup baseLibViewGroup) {
        for (WeakReference<BaseLibViewGroup> childViewGroup : childViewGroups) {
            if (baseLibViewGroup != null && childViewGroup.get() != null && baseLibViewGroup.equals(childViewGroup.get()) && baseLibViewGroup.hashCode() == baseLibViewGroup.hashCode()) {
                return;
            }
        }
        childViewGroups.add(new WeakReference<>(baseLibViewGroup));
    }

    /**
     * 页面跳转关闭软键盘
     */
    public void hideIME() {
        if (IMEUtils.isIMEAlive(this)) {
            IMEUtils.hideIME(this, Objects.requireNonNull(getCurrentFocus()));
        }
    }

    @Override
    protected void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().detachView();
        }
        super.onDestroy();
    }

}
