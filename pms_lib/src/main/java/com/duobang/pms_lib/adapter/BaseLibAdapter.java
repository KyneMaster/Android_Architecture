package com.duobang.pms_lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duobang.pms_lib.R;
import com.duobang.pms_lib.utils.MessageUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * ITEM 适配器
 */
public abstract class BaseLibAdapter<T, VH extends ViewHolder> extends RecyclerView.Adapter<VH> {

    /* 正在加载中 */
    protected static final int VIEW_TYPE_LOADING = 2;
    /* 内容为空的时候显示 */
    protected static final int VIEW_TYPE_EMPTY = 3;
    /* 是否显示空布局 */
    private boolean isShowEmpty = false;
    private String emptyTitle = "暂无数据";

    public interface onItemClickListener<T> {
        void onItemClick(Context context, int i, T item);
    }

    public interface onItemLongClickListener<T> {
        void onItemLongClick(Context context, int i, T item);
    }

    public interface OnEmptyClickListener {
        void onEmptyClick();
    }

    private List<T> mDataList;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private onItemClickListener<T> mOnItemClickListener;
    private onItemLongClickListener<T> mOnItemLongClickListener;
    private OnEmptyClickListener onEmptyClickListener;

    public BaseLibAdapter() {
    }

    public BaseLibAdapter(List<T> dataList) {
        mDataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        if (isNullData() && isShowEmpty) {
            return VIEW_TYPE_EMPTY;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        if (viewType == VIEW_TYPE_EMPTY) {
            return (VH) new EmptyViewHolder(mLayoutInflater.inflate(R.layout.empty_view, parent, false));
        }
        return onCreateViewHolder(mLayoutInflater, parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) {
            ((EmptyViewHolder) holder).title.setText(emptyTitle);
            if (onEmptyClickListener != null) {
                onBindEmptyItemClickListener((EmptyViewHolder) holder);
            }
        } else {
            final T dataItem = getDataItem(Math.max(0, position));
            onBindViewHolder(holder, position, dataItem);
            if (mOnItemClickListener != null && dataItem != null) {
                onBindItemClickListener(holder, position, dataItem);
            }
        }
    }

    protected void onBindItemClickListener(VH holder, final int position, final T dataItem) {

        // 加载中点击事件不处理
        if (getItemViewType(position) == VIEW_TYPE_LOADING)
            return;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v.getContext(), position, dataItem);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(v.getContext(), position, dataItem);
                }
                return false;
            }
        });
    }

    private void onBindEmptyItemClickListener(EmptyViewHolder holder) {
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmptyClickListener.onEmptyClick();
            }
        });
    }

    @Nullable
    public T getDataItem(int position) {
        position = Math.max(0, position);
        if (isNullData()) {
            return null;
        }
        return mDataList.get(position % mDataList.size());
    }

    public List<T> getDataList() {
        return mDataList;
    }

    @Override
    public int getItemCount() {
        int empty = isShowEmpty ? 1 : 0;
        if (isNullData()) {
            return empty;
        }
        return mDataList.size();
    }

    /**
     * 初始化布局文件
     *
     * @param parent   可以为空
     * @param layoutId 布局ID
     * @return 视图
     */
    protected View inflateView(ViewGroup parent, int layoutId) {
        return mLayoutInflater.inflate(layoutId, parent, false);
    }

    public Context getContext() {
        return mContext;
    }

    public abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH holder, int position, T item);

    public void setDataList(List<T> data) {
        if (mDataList != null && mDataList != data) {
            mDataList.clear();
            mDataList = null;
        }
        mDataList = data;
    }

    /**
     * 数据集是否为空
     *
     * @return
     */
    public boolean isNullData() {
        return mDataList == null || mDataList.size() <= 0;
    }

    /**
     * 通知数据集合发生改变
     * notifyDataSetChanged ——> ListView
     *
     * @param data 数据
     */
    public void invalidate(List<T> data) {
        setDataList(data);
        notifyDataSetChanged();
    }

    /**
     * 批量添加
     *
     * @param insertData 添加的列表
     */
    public void insertListData(List<T> insertData) {
        if (insertData == null) {
            return;
        }
        int index = 0;
        for (T data : insertData) {
            if (data != null) {
                mDataList.add(data);
                index++;
            }
        }
        notifyItemRangeInserted(mDataList.size() - index, index);
    }

    /**
     * 指定位置批量添加
     *
     * @param insertData 添加的列表
     */
    public void insertListData(int start, List<T> insertData) {
        if (insertData == null) {
            return;
        }
        int index = start;
        for (T data : insertData) {
            if (data != null) {
                mDataList.add(index, data);
                index++;
            }
        }
        notifyItemRangeInserted(start, index - start);
        notifyItemRangeChanged(0, mDataList.size());
    }

    /**
     * 添加单条数据
     *
     * @param insertedBean 添加的数据
     */
    public void insertData(T insertedBean) {
        if (insertedBean == null) {
            return;
        }
        mDataList.add(insertedBean);
        notifyItemRangeInserted(mDataList.size(), 1);
    }

    /**
     * 数据添加到顶部
     *
     * @param insertedBean 添加的数据
     */
    public void insertHeaderData(T insertedBean) {
        if (insertedBean == null) {
            return;
        }
        mDataList.add(0, insertedBean);
        notifyItemRangeInserted(0, 1);
        notifyItemRangeChanged(0, mDataList.size());
    }

    /**
     * 全局刷新
     *
     * @param poiItemList 新列表
     */
    public void notifyData(List<T> poiItemList) {
        if (poiItemList != null) {
            int previousSize = mDataList.size();
            mDataList.clear();
            notifyItemRangeRemoved(0, previousSize);
            mDataList.addAll(poiItemList);
            notifyItemRangeInserted(0, poiItemList.size());
        }
    }

    /**
     * 单条刷新
     *
     * @param item 要刷新的item数据
     * @param i    要刷新的item指针
     */
    public void notifyItem(T item, int i) {
        if (i < mDataList.size()) {
            mDataList.remove(i);
            mDataList.add(i, item);
            notifyItemRangeChanged(i, 1);
        }
    }

    /**
     * 批量移除、单条移除
     * 从指定位置向后移除一定长度的item
     *
     * @param start 开始位置
     * @param count 数量
     */
    public void removeData(int start, int count) {
        if ((start + count) > mDataList.size()) {
            return;
        }
        try {
            mDataList.removeAll(mDataList.subList(start, start + count));
            notifyItemRangeRemoved(start, count);
            notifyItemRangeChanged(0, mDataList.size());
        } catch (Exception e) {
            MessageUtils.shortToast("手机版本不支持移除，请手动刷新！！");
        }
    }

    /**
     * 清空内部数据集
     */
    public void clear() {
        if (mDataList != null)
            mDataList.clear();
    }

    /**
     * 获取数据实体所在的索引
     *
     * @param item 实体
     * @return 索引
     */
    public int getItemPosition(T item) {
        return mDataList.indexOf(item);
    }

    /**
     * 展示空布局，当数据为空时，默认不展示
     *
     * @param isShow 默认true
     */
    public void setDisplayEmpty(boolean isShow) {
        this.isShowEmpty = isShow;
    }

    /**
     * 设置空布局提示标题
     *
     * @param title
     */
    public void setEmptyTitle(String title) {
        this.emptyTitle = title;
    }

    /**
     * 设置ITEM的点击事件
     */
    public void setOnItemClickListener(onItemClickListener<T> listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(onItemLongClickListener<T> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setOnEmptyClickListener(OnEmptyClickListener onEmptyClickListener) {
        this.onEmptyClickListener = onEmptyClickListener;
    }

    public void destroy() {
        mDataList.clear();
        mLayoutInflater = null;
        mContext = null;
    }

    private static class EmptyViewHolder extends ViewHolder {
        TextView title;

        public EmptyViewHolder(@NonNull android.view.View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_empty);
        }
    }
}
