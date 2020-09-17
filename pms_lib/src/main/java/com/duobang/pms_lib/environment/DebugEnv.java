package com.duobang.pms_lib.environment;

import com.duobang.pms_lib.BuildConfig;

public class DebugEnv {
    public static boolean DEBUG = BuildConfig.DEBUG;
//    public static String DEBUG_URL = "https://pms.duobangbox.com/";
    public static String DEBUG_URL = "http://pms.duobangbox.cn/";
//    public static String RELEASE_URL = "http://pms.duobangbox.cn/";
    public static String RELEASE_URL = "https://pms.duobangbox.com/";
    public static final String BASE_URL = DEBUG ? DEBUG_URL : RELEASE_URL;
    public static String sentryDsn = "http://5cf7b7680c524d3991ddd07a1bafa14c@sentry.duodainfo.cn:9000/4";
}
