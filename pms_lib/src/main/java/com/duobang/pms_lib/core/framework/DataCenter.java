package com.duobang.pms_lib.core.framework;

import com.duobang.pms_lib.i.framework.IDataCenter;
import com.duobang.pms_lib.i.framework.OnDataChangeListener;

import java.util.HashMap;
import java.util.Stack;

public class DataCenter implements IDataCenter {
    private static volatile DataCenter instance;
    private final HashMap<Integer, OnDataChangeListener> listeners = new HashMap<Integer, OnDataChangeListener>();
    private final HashMap<Integer, Stack<OnDataChangeListener>> prelisteners = new HashMap<Integer, Stack<OnDataChangeListener>>();

    private DataCenter() {
    }

    public static DataCenter getInstance() {
        if (instance == null) {
            synchronized (DataCenter.class) {
                if (instance == null) {
                    instance = new DataCenter();
                }
            }
        }
        return instance;
    }

    @Override
    public void registerListener(int key, OnDataChangeListener onDataChangeListener) {
        OnDataChangeListener preListener = listeners.put(key, onDataChangeListener);
        if (preListener != null && !preListener.equals(onDataChangeListener)) {
            Stack<OnDataChangeListener> preListenerStack = prelisteners.get(key);
            if (preListenerStack == null) {
                preListenerStack = new Stack<OnDataChangeListener>();
            }
            preListenerStack.push(preListener);
            prelisteners.put(key, preListenerStack);
        }
    }

    @Override
    public void unregisterListener(int key) {
        if (listeners.containsKey(key)) {
            listeners.remove(key);
        } else {
            return;
        }
        Stack<OnDataChangeListener> preListenerStack = prelisteners.get(key);
        if (preListenerStack != null && !preListenerStack.isEmpty()) {
            OnDataChangeListener preListener = preListenerStack.pop();
            if (preListenerStack.isEmpty()) {
                prelisteners.remove(key);
            }
            registerListener(key, preListener);
        }
    }

    @Override
    public void notifyDataChange(int key) {
        if (listeners.get(key) != null) {
            listeners.get(key).onDataChange(key);
        }
    }

    @Override
    public void onPause(int args) {
        if (args == 0) {
            for (Integer key : listeners.keySet()) {
                listeners.get(key).onPause();
            }
        } else if (listeners.containsKey(args)) {
            listeners.get(args).onPause();
        }
    }

    @Override
    public void onResume(int args) {
        if (args == 0) {
            for (Integer key : listeners.keySet()) {
                listeners.get(key).onResume();
            }
        } else if (listeners.containsKey(args)) {
            listeners.get(args).onResume();
        }
    }

    @Override
    public synchronized void onDestroy(int args) {
        if (args == 0) {
            HashMap<Integer, OnDataChangeListener> temp = new HashMap<Integer, OnDataChangeListener>(listeners);
            for (Integer key : temp.keySet()) {
                if (listeners.get(key) != null) {
                    listeners.get(key).onDestroy();
                }
            }
        } else if (listeners.containsKey(args)) {
            listeners.get(args).onDestroy();
        }
    }

    @Override
    public void requestLoadData(int key) {
        if (listeners.containsKey(key)) {
            listeners.get(key).requestLoadData(key);
        }
    }

}
