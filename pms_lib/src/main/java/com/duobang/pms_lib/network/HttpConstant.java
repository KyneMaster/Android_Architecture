package com.duobang.pms_lib.network;

public class HttpConstant {

    public static final int HTTP_CACHE_SIZE = 10 * 1024 * 1024;
    public static final int CONNECT_TIMEOUT = 10;
    public static final int READ_TIMEOUT = 20;
    public static final int WRITE_TIMEOUT = 20;
    /**
     * 离线时缓存保存1周,单位:秒
     */
    protected static final int MAX_STALE = 7 * 24 * 60;
    /**
     * 有网最大缓存时间
     */
    protected static final int NET_MAX_STALE = 0;
}
