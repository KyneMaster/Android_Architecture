package com.duobang.pms_lib.i.framework;

import java.util.List;

public interface OnDataChangeListener {
    /**
     * 获得当前view数据刷新任务集合
     *
     * @return
     */
    List<IDataRefreshTask> getDataRefreshTasks();

    /**
     * 数据发生变化时会被通知到
     *
     * @param taskId
     */
    void onDataChange(int taskId);

    /**
     * 立即加载指定taskId的task，不会周期性加载
     *
     * @param taskId
     */
    void requestLoadData(int taskId);

    void onRequestLoadData(int taskId);

    void onDestroy();

    void onPause();

    void onResume();

}
