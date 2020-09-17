package com.duobang.middleware.weight;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.duobang.middleware.R;
import com.duobang.pms_lib.adapter.BaseLibAdapter;
import com.duobang.pms_lib.common.DuobangLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 下拉多选组件
 *
 * 下拉列表需要外部自己实现
 *
 * TODO 下一步实现自定义流式布局，使用view添加形式，根据子View测量
 *      1、当前内部列表的长度无法控制,只能使用recylerView的瀑布式布局
 *      2、负布局需要根据子view测量宽高
 */
public class MultipleSpinner extends FrameLayout {

    private Context mContext;

    private RecyclerView innerView;
    private InnerAdapter innerAdapter;
    private List<String> innerData;

    private RecyclerView dropDownView;
    private BaseLibAdapter dropAdapter;

    private PopupWindow popupWindow;

    public MultipleSpinner(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public MultipleSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public MultipleSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        initInnerView();
        setBackgroundResource(R.drawable.fill_stroke_background);
    }

    private void initInnerView() {
        View view = View.inflate(mContext, R.layout.list_layout, null);
        innerView = view.findViewById(R.id.list_layout);
        setupInnerView();
        addView(view);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        showMenu();
        return true;
    }

    private void setupInnerView() {
        innerData = new ArrayList<>();
        innerAdapter = new InnerAdapter(innerData);
//        innerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        innerView.setLayoutManager(new DuobangLinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        innerView.setAdapter(innerAdapter);
    }

    private void showMenu() {
        popupWindow = new PopupWindow(mContext);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = View.inflate(mContext, R.layout.multiple_spinner_drop_view, null);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(this);
        dropDownView = view.findViewById(R.id.list_multiple_spinner_drop);
        setupDropView();
    }

    private void setupDropView() {
        if (dropAdapter != null && dropDownView != null) {
            dropDownView.setLayoutManager(new DuobangLinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            dropDownView.setAdapter(dropAdapter);
        }
    }

    /**
     * 往内部列表添加数据
     *
     * @param item
     */
    public void invalidate(String item){
        if (!innerData.contains(item)){
            innerData.add(item);
            innerAdapter.invalidate(innerData);
        }else {
            innerData.remove(item);
            innerAdapter.invalidate(innerData);
        }
    }

    /**
     * 设置下拉View adapter
     *
     * @param adapter
     * @param <T>
     */
    public <T extends BaseLibAdapter> void setDropAdapter(T adapter) {
        dropAdapter = adapter;
        invalidate();
    }

    /**
     * 返回内部adapter
     *
     * @return
     */
    public InnerAdapter getInnerAdapter() {
        return innerAdapter;
    }

    /**
     * 返回下拉adapter
     *
     * @return
     */
    public BaseLibAdapter getDropAdapter() {
        return dropAdapter;
    }

    public class InnerAdapter extends BaseLibAdapter<String, InnerViewHolder> {

        public InnerAdapter(List<String> dataList) {
            super(dataList);
        }

        @Override
        public int getItemCount() {
            return innerData != null ? innerData.size() : 0;
        }

        @Override
        public InnerViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            return new InnerViewHolder(inflater.inflate(R.layout.multiple_spinner_inner_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(InnerViewHolder holder, final int position, String item) {
            holder.tv.setText(item);
        }
    }

    public class InnerViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.multiple_spinner_item);
        }
    }

}
