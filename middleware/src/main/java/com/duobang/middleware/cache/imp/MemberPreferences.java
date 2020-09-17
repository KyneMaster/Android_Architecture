package com.duobang.middleware.cache.imp;

import com.duobang.middleware.constant.ISpConstant;
import com.duobang.middleware.i.cache.IMemberPreferences;
import com.duobang.pms_lib.core.sp.DefaultPreference;
import com.duobang.pms_lib.utils.JsonUtil;

import org.json.JSONException;

import java.util.List;

public class MemberPreferences extends DefaultPreference implements IMemberPreferences {

    private static final String TAG = "MemberPreferences";

    public MemberPreferences() {
        super();
    }

    @Override
    public String getPreferenceName() {
        return TAG;
    }

    @Override
    public <T> List<T> getMembers(Class<?> MemberClass) {
        String json = getPreferences().getString(ISpConstant.MEMBER.LIST, null);
        try {
            return json != null ? (List<T>) JsonUtil.toList(json, MemberClass) : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> void saveMembers(List<T> members) {
        String json = JsonUtil.toJson(members);
        getPreferences().putString(ISpConstant.MEMBER.LIST, json).commit();
    }

    @Override
    public void clear() {
        super.clear();
    }
}
