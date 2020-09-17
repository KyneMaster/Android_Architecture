package com.duobang.pms_lib.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class DuobangListView extends ListView {

    public DuobangListView(Context context) {
        super(context);
    }

    public DuobangListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DuobangListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec
                ,MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST));
    }
}
