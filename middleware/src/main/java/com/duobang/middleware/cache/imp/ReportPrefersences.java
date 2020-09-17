package com.duobang.middleware.cache.imp;

import com.duobang.middleware.constant.ISpConstant;
import com.duobang.middleware.i.cache.IReportPrefersences;
import com.duobang.pms_lib.core.sp.DefaultPreference;
import com.duobang.pms_lib.utils.JsonUtil;

import org.json.JSONException;

import java.util.List;

public class ReportPrefersences extends DefaultPreference implements IReportPrefersences {

    private static final String TAG = "ReportPrefersences";

    public ReportPrefersences() {
        super();
    }

    @Override
    public String getPreferenceName() {
        return TAG;
    }

    @Override
    public <T> List<T> getReportUsers(Class<?> ReportUserClass) {
        String json = getPreferences().getString(ISpConstant.REPORT.LIST, null);
        try {
            return json != null ? (List<T>) JsonUtil.toList(json, ReportUserClass) : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> void saveReportUsers(List<T> ReportUsers) {
        String json = JsonUtil.toJson(ReportUsers);
        getPreferences().putString(ISpConstant.REPORT.LIST, json).commit();
    }

    @Override
    public void clear() {
        super.clear();
    }
}
