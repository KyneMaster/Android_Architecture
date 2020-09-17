package com.duobang.pms_lib.i.sp;

public interface IPreference {

    ISharePreferences getPreferences();

    void commit();

    void clear();

    void remove(String key);
}
