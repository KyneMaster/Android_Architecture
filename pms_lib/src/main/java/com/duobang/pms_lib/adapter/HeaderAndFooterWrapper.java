package com.duobang.pms_lib.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 装饰者模式为列表添加头布局和底布局
 */
public class HeaderAndFooterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int BASE_ITEM_TYPE_HEADER = 100000;
    public static final int BASE_ITEM_TYPE_FOOTER = 200000;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mInnerAdapter;


    public HeaderAndFooterWrapper(RecyclerView.Adapter mInnerAdapter) {
        this.mInnerAdapter = mInnerAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (mHeaderViews.get(viewType) != null){
            return new MyViewHolder(mHeaderViews.get(viewType));
        }else if (mFooterViews.get(viewType) != null){
            return new MyViewHolder(mFooterViews.get(viewType));
        }
        return mInnerAdapter.onCreateViewHolder(viewGroup,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (isHeaderViewPos(i))
            return;
        if (isFooterViewPos(i))
            return;
        mInnerAdapter.onBindViewHolder(viewHolder,i - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)){
            return mHeaderViews.keyAt(position);
        }else if (isFooterViewPos(position)){
            return mFooterViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    /**
     * 获取正常数据的size
     * @return
     */
    private int getRealItemCount(){
        return mInnerAdapter.getItemCount();
    }

    /**
     * 判断是否是Header
     * @param position
     * @return
     */
    private boolean isHeaderViewPos(int position){
        return position < getHeadersCount();
    }

    /**
     * 判断是否是Footer
     * @param position
     * @return
     */
    private boolean isFooterViewPos(int position){
        return position >= getHeadersCount() + getRealItemCount();
    }


    public void addHeaderView(View view){
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER,view);
    }

    public void addFooterView(View view){
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER,view);
    }

    public int getHeadersCount(){
        return mHeaderViews.size();
    }

    public int getFootersCount(){
        return mFooterViews.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

}
