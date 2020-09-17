package com.duobang.pms_lib.core.sp;

import com.duobang.pms_lib.i.sp.IPreference;
import com.duobang.pms_lib.i.sp.ISharePreferences;

public class DefaultPreference implements IPreference {

    private String TAG = "DefaultPreference";
    private ISharePreferences sharePreferences;

    public DefaultPreference(){
        sharePreferences = new DuobangPreference(getPreferenceName());
    }

    public String getPreferenceName() {
        return TAG;
    }

    @Override
    public ISharePreferences getPreferences() {
        return sharePreferences;
    }

    @Override
    public void commit() {
        getPreferences().commit();
    }

    @Override
    public void clear() {
        getPreferences().clear();
    }

    @Override
    public void remove(String key) {
        getPreferences().remove(key);
    }


}
