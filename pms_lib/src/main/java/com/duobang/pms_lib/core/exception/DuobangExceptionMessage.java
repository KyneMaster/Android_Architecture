package com.duobang.pms_lib.core.exception;


import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class DuobangExceptionMessage {

    public final static int EXCEPTION_SERVICE_FORMAT_ERROR = -1;

    public final static int EXCEPTION_SERVICE_NETWORK_FORMAT_ERROR = -2;

    public final static int MORE_EXCEPTION_SERVICE_INFO_CONTENT_NULL_ERROR = -3;

    public final static int MORE_EXCEPTION_SUCCESS_NO_DATA = -4;

    protected static final Map<Integer, String> MSGS = new HashMap<>();

    static {
        MSGS.put(EXCEPTION_SERVICE_FORMAT_ERROR, "网络异常");
        MSGS.put(EXCEPTION_SERVICE_NETWORK_FORMAT_ERROR, "数据获取异常");
        MSGS.put(MORE_EXCEPTION_SERVICE_INFO_CONTENT_NULL_ERROR, "数据获取失败");
        MSGS.put(MORE_EXCEPTION_SUCCESS_NO_DATA, "没有更多数据了");

        MSGS.put(1000001, "解析异常");
    }

    public static String getMessage(int code, String message) {
        if (TextUtils.isEmpty(message)) {
            message = MSGS.containsKey(code) ? MSGS.get(code) : "操作失败";
        } else {
            message = MSGS.containsKey(code) ? MSGS.get(code) : message;
        }
        return message;
    }
}
