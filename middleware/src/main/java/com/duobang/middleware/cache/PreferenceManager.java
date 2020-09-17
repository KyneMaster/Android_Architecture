package com.duobang.middleware.cache;

import android.content.Context;

import com.duobang.middleware.cache.imp.AppPreferences;
import com.duobang.middleware.cache.imp.MemberPreferences;
import com.duobang.middleware.cache.imp.ReportPrefersences;
import com.duobang.middleware.cache.imp.UserPreferences;
import com.duobang.middleware.i.cache.IAppPreferences;
import com.duobang.middleware.i.cache.IMemberPreferences;
import com.duobang.middleware.i.cache.IReportPrefersences;
import com.duobang.middleware.i.cache.IUserPreferences;
import com.duobang.pms_lib.cache.AppCacheManager;
import com.duobang.pms_lib.core.sp.BasePreferenceManager;
import com.tencent.mmkv.MMKV;

public class PreferenceManager extends BasePreferenceManager {

    private static volatile PreferenceManager instance;
    private IUserPreferences userPreferences;
    private IMemberPreferences memberPreferences;
    private IAppPreferences appPreferences;
    private IReportPrefersences reportPrefersences;
    private static final String TAG = "PreferenceManager";

    public static PreferenceManager getInstance() {
        if (instance == null) {
            synchronized (PreferenceManager.class) {
                if (instance == null) {
                    instance = new PreferenceManager();
                }
            }
        }
        return instance;
    }

    public PreferenceManager() {
        AppCacheManager.getInstance().addCacheOperateListener(TAG, this);
    }

    public IUserPreferences getUserPreferences() {
        return userPreferences;
    }

    private void setUserPreferences(IUserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }

    public IMemberPreferences getMemberPreferences() {
        return memberPreferences;
    }

    public void setMemberPreferences(IMemberPreferences memberPreferences) {
        this.memberPreferences = memberPreferences;
    }

    public IAppPreferences getAppPreferences() {
        return appPreferences;
    }

    public void setAppPreferences(IAppPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }

    public IReportPrefersences getReportPrefersences() {
        return reportPrefersences;
    }

    public void setReportPrefersences(IReportPrefersences reportPrefersences) {
        this.reportPrefersences = reportPrefersences;
    }

    public void initPreferences(Context context) {
        MMKV.initialize(context);
        PreferenceManager.getInstance().setUserPreferences(new UserPreferences());
        PreferenceManager.getInstance().setMemberPreferences(new MemberPreferences());
        PreferenceManager.getInstance().setAppPreferences(new AppPreferences());
        PreferenceManager.getInstance().setReportPrefersences(new ReportPrefersences());
    }

    @Override
    public void clearData() {
        appPreferences.clear();
    }

    public void logout(){
        userPreferences.clear();
        memberPreferences.clear();
        appPreferences.clear();
    }
}
