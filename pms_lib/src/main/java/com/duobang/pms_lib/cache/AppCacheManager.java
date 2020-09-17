package com.duobang.pms_lib.cache;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class AppCacheManager {
    private static AppCacheManager instance;
    private final HashMap<String, CacheOperateListener> listeners = new HashMap<String, CacheOperateListener>();

    private AppCacheManager() {
    }

    public static AppCacheManager getInstance() {
        if (instance == null) {
            instance = new AppCacheManager();
        }
        return instance;
    }

    public void addCacheOperateListener(String key, CacheOperateListener listener) {
        if (listeners.containsKey(key)) {
            throw new RuntimeException("数据管理重复注册");
        }
        listeners.put(key, listener);
    }

    public synchronized void logout() {
        clearMemoryCache();
    }

    public void clearMemoryCache() {
        Set<String> keys = listeners.keySet();
        for (String key : keys) {
            Objects.requireNonNull(listeners.get(key)).clearData();
        }
    }

    public interface CacheOperateListener {
        void clearData();
    }
}
