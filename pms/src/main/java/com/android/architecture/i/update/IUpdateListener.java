package com.android.architecture.i.update;

import com.android.architecture.core.update.Update;

public interface IUpdateListener {

    void onUpdateSuccess(Update update);

    void onFailure(String message);
}
