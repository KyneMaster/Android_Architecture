package com.duobang.middleware.common;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * 日期时间选择器工具类
 */
public class AppDatePicker {

    /**
     * 打开日期时间选择器
     *
     * @param tv 渲染的TextView
     */
    public static void showDateAndTimePicker(final TextView tv) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(tv.getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                showTimePicker(tv, date);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 打开日期选择器
     */
    public static void showDatePicker(final TextView tv) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(tv.getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                tv.setText(date);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 打开日期选择器,带监听
     */
    public static void showDatePicker(final TextView tv, final OnDatePickerListener listener) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(tv.getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                tv.setText(date);
                listener.onDatePicker(date);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 打开时间选择器
     */
    public static void showTimePicker(final TextView tv, final String date) {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(tv.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String dateTime = date + " " + hourOfDay + ":" + minute;
                tv.setText(dateTime);
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }

    /**
     * 年月选择器
     *
     * @param tv
     */
    public static void showMonthPicker(final TextView tv) {
        Calendar c = Calendar.getInstance();
        new MonthPickerDialog(tv.getContext(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month + 1);
                tv.setText(date);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONDAY), c.get(Calendar.DATE)).show();
    }

    public interface OnDatePickerListener{
        void onDatePicker(String date);
    }
}
