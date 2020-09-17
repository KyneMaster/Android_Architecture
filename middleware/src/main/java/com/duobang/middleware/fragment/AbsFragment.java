package com.duobang.middleware.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class AbsFragment extends Fragment {
    private static final String TAG = "AbsFragment";
    protected View mRoot = null;

    /******
     * Fragment是否创建
     * **/
    protected boolean mViewCreate = false;

    /*******
     * 当前fragment状态
     * **/
    protected boolean mVisibleState = false;

    protected abstract int getLayoutId();

    protected abstract void initViews();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mRoot) {
            mRoot = inflater.inflate(getLayoutId(), container, false);
        } else {
            ViewGroup vg = (ViewGroup) mRoot.getParent();
            if (null != vg) {
                vg.removeView(mRoot);
            }
        }
        mViewCreate = true;
        initViews();
        if (getUserVisibleHint() && !isHidden()) {
            dispatchVisibleHint(true);
        }
        return mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        //判断从不可见获取到焦点
        if (!mVisibleState && !getUserVisibleHint() && !isHidden()) {
            dispatchVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //判断当前状态是从可见失去焦点
        if (mVisibleState && getUserVisibleHint()) {
            dispatchVisibleHint(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mViewCreate) {
            dispatchVisibleHint(isVisibleToUser);
        }
    }

    /**********
     * 前台运行时，加载数据
     * ***/
    protected void loadingDatas() {
        Log.d(TAG, "loadingDatas function start");
    }

    /*********
     * 切换至后台运行时，停止加载数据
     * ****/
    protected void stopLoading() {
        Log.d(TAG, "stopLoading function start");
    }

    /***********
     * 处理fragment数据加载
     * ***/
    private void dispatchVisibleHint(boolean isVisibleHint) {
        if (mVisibleState != isVisibleHint) {
            mVisibleState = isVisibleHint;
            if (isVisibleHint) {
                loadingDatas();
            } else {
                stopLoading();
            }
        }
    }
}
