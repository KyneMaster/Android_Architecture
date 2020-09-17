package com.duobang.pms_lib.framework;

import android.content.Context;

import com.duobang.pms_lib.i.framework.IPresenter;
import com.duobang.pms_lib.i.framework.PresenterFactory;

import androidx.annotation.NonNull;
import androidx.loader.content.Loader;

public class PresenterLoader<T extends IPresenter> extends Loader<T> {

    private final PresenterFactory<T> factory;

    private T presenter;

    public PresenterLoader(@NonNull Context context, PresenterFactory<T> factory) {
        super(context);
        this.factory = factory;
    }

    @Override
    protected void onStartLoading() {
        if (presenter != null) {
            deliverResult(presenter);
            return;
        }
        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        presenter = factory.create();
        deliverResult(presenter);
    }

    @Override
    protected void onReset() {
        presenter = null;
    }
}
