package com.duobang.pms_lib.utils;

import java.util.List;

import androidx.annotation.Nullable;

public class EmptyUtils {

    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static <T> boolean isEmpty(@Nullable List<T> list) {
        return list == null || list.size() == 0;
    }

}
