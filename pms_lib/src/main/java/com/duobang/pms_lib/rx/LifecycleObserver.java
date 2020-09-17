package com.duobang.pms_lib.rx;

import androidx.annotation.NonNull;

public interface LifecycleObserver {

    @NonNull
    LifecycleProvider getLifecycleProvider();
}
