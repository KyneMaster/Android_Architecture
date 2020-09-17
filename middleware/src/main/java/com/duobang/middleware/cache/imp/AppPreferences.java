package com.duobang.middleware.cache.imp;

import com.duobang.middleware.constant.ISpConstant;
import com.duobang.middleware.i.cache.IAppPreferences;
import com.duobang.pms_lib.core.sp.DefaultPreference;

/**
 * 通用应用的缓存
 */
public class AppPreferences extends DefaultPreference implements IAppPreferences {

    private static final String TAG = "AppPreferences";

    @Override
    public String getPreferenceName() {
        return TAG;
    }

    @Override
    public String getTaskOperator() {
        return  getPreferences().getString(ISpConstant.APP.TASK_OPERATOR, null);
    }

    @Override
    public void saveTaskOperator(String userId) {
        getPreferences().putString(ISpConstant.APP.TASK_OPERATOR, userId).commit();
    }

    @Override
    public void clear() {
        super.clear();
    }
}
