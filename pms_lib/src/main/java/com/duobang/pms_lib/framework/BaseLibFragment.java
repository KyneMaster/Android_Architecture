package com.duobang.pms_lib.framework;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duobang.pms_lib.i.framework.IBaseView;
import com.duobang.pms_lib.i.framework.IPresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public abstract class BaseLibFragment<P extends IPresenter<V>, V extends IBaseView> extends Fragment implements View.OnClickListener {

    protected FragmentActivity mActivity;
    protected View mRootView;
    protected P presenter;
    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完onCreateView,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = onCreatePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resID = setLayoutResID();
        if (resID != 0) {
            mRootView = inflater.inflate(resID, container, false);
        } else {
            throw new RuntimeException("content view not found");
        }
        attachPresenter();
        init();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 此方法用于初始化成员变量及获取Bundle传递过来的数据
     */
    protected void init() {
        if (handleBundle()) {
            initView();
            initData();
            mIsPrepare = true;
            onVisibleToUser();
        } else {
            mActivity.finish();
        }
    }

    protected void attachPresenter(){
        if (getPresenter() != null) {
            getPresenter().attachView((V) this);
        }
    }

    /**
     * 返回presenter
     *
     * @return
     */
    public P getPresenter(){
        return presenter;
    }

    /**
     * 返回Fragment设置ContentView的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    protected abstract int setLayoutResID();

    /**
     * 从Bundle中获取数据加载数据
     * <p/>
     * return false如果入参发生问题，需要关闭本页面
     */
    protected boolean handleBundle() {
        return true;
    }

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected void initData(){

    }

    /**
     * 实例化presenter
     */
    public abstract P onCreatePresenter();

    protected View.OnClickListener getOnClickListener() {
        return this;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisible = isVisibleToUser;
        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.mIsVisible = !hidden;
        if (!hidden) {
            onVisibleToUser();
        }
    }


    /**
     * 用户可见时执行的操作
     */
    protected void onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            onStartLoadData();
        }
    }

    /**
     * 懒加载，仅当用户可见切view初始化结束后才会执行
     */
    protected abstract void onStartLoadData();

    public View getRootView() {
        return mRootView;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id) {
        if (getRootView() == null) {
            return null;
        }
        return (T) getRootView().findViewById(id);
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        mIsVisible = false;
//        mIsPrepare = false;
//    }
}
