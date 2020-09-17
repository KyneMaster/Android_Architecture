package com.duobang.middleware.common;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MonthPickerDialog extends DatePickerDialog {


    @RequiresApi(api = Build.VERSION_CODES.N)
    public MonthPickerDialog(@NonNull Context context) {
        super(context);
        ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MonthPickerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        View childAt = ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2);
        if (childAt != null){
            childAt.setVisibility(View.GONE);
        }
    }

    public MonthPickerDialog(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
        View childAt = ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2);
        if (childAt != null){
            childAt.setVisibility(View.GONE);
        }
    }

    public MonthPickerDialog(@NonNull Context context, int theme, @Nullable OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, theme, listener, year, monthOfYear, dayOfMonth);
        View childAt = ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2);
        if (childAt != null){
            childAt.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDateChanged(@NonNull DatePicker view, int year, int month, int dayOfMonth) {
        super.onDateChanged(view, year, month, dayOfMonth);
        this.setTitle(year + "年");
    }
}
