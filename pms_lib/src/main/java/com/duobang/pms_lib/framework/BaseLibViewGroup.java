package com.duobang.pms_lib.framework;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.duobang.pms_lib.core.framework.DataCenter;
import com.duobang.pms_lib.core.framework.MessageData;
import com.duobang.pms_lib.i.framework.IDataRefreshTask;
import com.duobang.pms_lib.i.framework.IView;
import com.duobang.pms_lib.i.framework.OnDataChangeListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class BaseLibViewGroup implements IView, OnDataChangeListener, Handler.Callback {
    private static final int MSG_REFRESH_DATA_PERIOD = 1000;
    private static final int MSG_REQUEST_LOAD_DATA = 1001;
    protected final ArrayList<IView> children = new ArrayList<>();
    private final Context context;
    private final Handler innerHandler = new InnerHandler(Looper.getMainLooper(), this);
    private final Handler outerHandler = new OuterHandler(Looper.getMainLooper(), this);
    private ViewGroup rootView;
    private OnClickListener onClickListener;
    private List<MessageData> innerMsgData = new ArrayList<MessageData>();

    public BaseLibViewGroup(Context context) {
        this.context = context;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public Handler getHandler() {
        return outerHandler;
    }

    private Handler getInnerHandler() {
        return innerHandler;
    }

    public void onScrollToTop() {
    }

    @Override
    public Context getContext() {
        if (context == null) {
            throw new RuntimeException("context not found");
        }
        return context;
    }

    @Override
    public void createRootView(ViewGroup parent) {
        int rootViewResId = getRootViewResId();
        if (rootViewResId == 0) {
            throw new RuntimeException("root view res id not found");
        }
        View rootView = LayoutInflater.from(getContext()).inflate(rootViewResId, parent, false);
        setRootView(rootView);
    }

    @Override
    public int getRootViewResId() {
        return 0;
    }

    @Override
    public ViewGroup getRootView() {
        if (rootView == null) {
            throw new RuntimeException("root view not found");
        }
        return rootView;
    }

    @Override
    public void setRootView(View rootView) {
        this.rootView = (ViewGroup) rootView;
    }

    @Override
    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onRequestLoadData(int key) {

    }

    @Override
    public void onInitChildren() {

    }

    @Override
    public void init() {
        onInitChildren();
        View view = getRootView();
        if (view != null) {
            view.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {

                @Override
                public void onViewAttachedToWindow(View view) {
                    onAttachedToWindow(view);
                }

                @Override
                public void onViewDetachedFromWindow(View view) {
                    onDetachedFromWindow(view);
                }
            });
        }
    }

    private synchronized void requestLoadData() {
        List<IDataRefreshTask> tasks = getDataRefreshTasks();
        if (tasks != null && tasks.size() > 0) {
            getInnerHandler().removeCallbacksAndMessages(null);
            for (IDataRefreshTask task : tasks) {
                requestLoadData(task.getTaskId(), task.getRefreshPeriod());
            }
        }
    }

    @Override
    public final void requestLoadData(int taskId) {
        requestLoadData(taskId, 0);
    }

    private void requestLoadData(int taskId, int refreshPeriod) {
        DataCenter.getInstance().registerListener(taskId, BaseLibViewGroup.this);
        onRequestLoadData(taskId);
        if (refreshPeriod != 0) {
            if (getContext() instanceof BaseLibActivity) {
                ((BaseLibActivity) getContext()).addChildView(this);
            }
            Message innerMsg = Message.obtain(getInnerHandler(), MSG_REFRESH_DATA_PERIOD, taskId, refreshPeriod);
            getInnerHandler().sendMessageDelayed(innerMsg, refreshPeriod);
            MessageData messageData = new MessageData();
            messageData.setWhat(innerMsg.what);
            messageData.setArg1(innerMsg.arg1);
            messageData.setArg2(innerMsg.arg2);
            innerMsgData.add(messageData);
        }
    }

    @Override
    public void onDestroy() {
        removeInnerCallbacksAndMessages();
        unRegisterListenerFromDataCenter();
    }

    @Override
    public void onPause() {
        removeInnerCallbacksAndMessages();
    }

    @Override
    public void onResume() {
        removeInnerCallbacksAndMessages();
        resumeInnerCallbacksAndMessages();
    }

    private void resumeInnerCallbacksAndMessages() {
        for (MessageData messageData : innerMsgData) {
            if (messageData.getWhat() == MSG_REFRESH_DATA_PERIOD) {
                Message message = Message.obtain(getInnerHandler(), messageData.getWhat(), messageData.getArg1(), messageData.getArg2());
                getInnerHandler().sendMessageDelayed(message, message.arg2);
            }
        }
    }

    @Override
    public void onRefresh(int viewId) {

    }

    @Override
    public List<IDataRefreshTask> getDataRefreshTasks() {
        return null;
    }

    @Override
    public void onDataChange(int key) {
    }

    @Override
    public boolean handleBackKeyPressed() {
        return false;
    }

    @Override
    public void onAttachedToWindow(View v) {
        getInnerHandler().sendEmptyMessage(MSG_REQUEST_LOAD_DATA);
    }

    @Override
    public void onDetachedFromWindow(View v) {
        innerMsgData.clear();
        removeInnerCallbacksAndMessages();
        unRegisterListenerFromDataCenter();
        getHandler().removeCallbacksAndMessages(null);
    }

    private void removeInnerCallbacksAndMessages() {
        getInnerHandler().removeCallbacksAndMessages(null);
    }

    private void unRegisterListenerFromDataCenter() {
        List<IDataRefreshTask> tasks = getDataRefreshTasks();
        if (tasks != null && tasks.size() > 0) {
            for (IDataRefreshTask task : tasks) {
                DataCenter.getInstance().unregisterListener(task.getTaskId());
            }
        }
    }

    public String getTag() {
        return "BaseLibViewGroup";
    }

    private static class BaseHandler extends Handler {
        protected final WeakReference<BaseLibViewGroup> outerReference;

        public BaseHandler(Looper looper, BaseLibViewGroup outerReference) {
            super(looper);
            this.outerReference = new WeakReference<>(outerReference);
        }

        @Override
        public void handleMessage(Message msg) {
        }
    }

    private static class InnerHandler extends BaseHandler {

        public InnerHandler(Looper looper, BaseLibViewGroup outerReference) {
            super(looper, outerReference);
        }

        @Override
        public void handleMessage(Message msg) {
            if (outerReference.get() != null) {
                switch (msg.what) {
                    case MSG_REFRESH_DATA_PERIOD:
                        outerReference.get().onRequestLoadData(msg.arg1);
                        Message newMsg = Message.obtain(msg);
                        sendMessageDelayed(newMsg, msg.arg2);
                        break;
                    case MSG_REQUEST_LOAD_DATA:
                        outerReference.get().requestLoadData();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private static class OuterHandler extends BaseHandler {

        public OuterHandler(Looper looper, BaseLibViewGroup outerReference) {
            super(looper, outerReference);
        }

        @Override
        public void handleMessage(Message msg) {
            if (outerReference.get() != null) {
                outerReference.get().handleMessage(msg);
            }
        }
    }

}
