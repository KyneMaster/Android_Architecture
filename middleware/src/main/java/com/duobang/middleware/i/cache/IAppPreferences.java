package com.duobang.middleware.i.cache;

public interface IAppPreferences {

    String getTaskOperator();

    void saveTaskOperator(String userId);

    void clear();
}
