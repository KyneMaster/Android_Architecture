package com.duobang.pms_lib.i.framework;

public interface IDataCenter {
    void registerListener(int key, OnDataChangeListener onDataChangeListener);

    void unregisterListener(int key);

    void notifyDataChange(int key);

    void requestLoadData(int key);

    void onPause(int key);

    void onResume(int key);

    void onDestroy(int key);

}
