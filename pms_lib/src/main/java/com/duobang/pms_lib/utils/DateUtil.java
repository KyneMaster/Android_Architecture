package com.duobang.pms_lib.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    private static final String TAG = "DateUtil";
    private static SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
    private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static SimpleDateFormat sdfMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    private static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static SimpleDateFormat sdfOnlyTime = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    private static SimpleDateFormat sdfDayMinute = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
    private static SimpleDateFormat sdfMinuteTime = new SimpleDateFormat("HH:mm", Locale.CHINA);
    private static SimpleDateFormat sdfMonthDay = new SimpleDateFormat("MM-dd", Locale.CHINA);

    public static String getCurrentDate(){
        return sdfDate.format(new Date(System.currentTimeMillis()));
    }

    public static String getCurrentMinute(){
        return sdfMinute.format(new Date(System.currentTimeMillis()));
    }

    public static String getCurrentTime(){
        return sdfTime.format(new Date(System.currentTimeMillis()));
    }

    public static String getCurrentOnlyTime(){
        return sdfOnlyTime.format(new Date(System.currentTimeMillis()));
    }

    public static String formatMonth(Date date) {
        String dateStr = null;
        try {
            dateStr = sdfMonth.format(date);
        } catch (Exception e) {
            Log.i(TAG, "stringToDate: " + e);
        }
        return dateStr;
    }

    public static String formatDate(Date date) {
        String dateStr = null;
        try {
            dateStr = sdfDate.format(date);
        } catch (Exception e) {
            Log.i(TAG, "stringToDate: " + e);
        }
        return dateStr;
    }

    public static String formatMinute(Date date) {
        String dateStr = null;
        try {
            dateStr = sdfMinute.format(date);
        } catch (Exception e) {
            Log.i(TAG, "stringToDate: " + e);
        }
        return dateStr;
    }

    public static String formatDayMinute(Date date) {
        String dateStr = null;
        try {
            dateStr = sdfDayMinute.format(date);
        } catch (Exception e) {
            Log.i(TAG, "stringToDate: " + e);
        }
        return dateStr;
    }

    public static String formatMonthDay(Date date) {
        String dateStr = null;
        try {
            dateStr = sdfMonthDay.format(date);
        } catch (Exception e) {
            Log.i(TAG, "stringToDate: " + e);
        }
        return dateStr;
    }

    public static Date parseDate(String str) {
        Date date = null;
        try {
            date = sdfDate.parse(str);
        } catch (Exception e) {
            Log.i(TAG, "stringToDate: " + e);
        }
        return date;
    }

    public static Date parseMonth(String str) {
        Date date = null;
        try {
            date = sdfMonth.parse(str);
        } catch (Exception e) {
            Log.i(TAG, "stringToDate: " + e);
        }
        return date;
    }

    public static String formatTime(Date date) {
        String dateStr = null;
        try {
            dateStr = sdfTime.format(date);
        } catch (Exception e) {
            Log.i(TAG, "stringToDate: " + e);
        }
        return dateStr;
    }

    public static String formatMinuteTime(Date date) {
        String dateStr = null;
        try {
            dateStr = sdfMinuteTime.format(date);
        } catch (Exception e) {
            Log.i(TAG, "stringToDate: " + e);
        }
        return dateStr;
    }

    public static Date parseTime(String str) {
        Date date = null;
        try {
            date = sdfTime.parse(str);
        } catch (Exception e) {
            Log.i(TAG, "stringToDate: " + e);
        }
        return date;
    }

    public static Date parseOnlyTime(String str) {
        Date date = null;
        try {
            date = sdfOnlyTime.parse(str);
        } catch (Exception e) {
            Log.i(TAG, "stringToDate: " + e);
        }
        return date;
    }


    public static Date parseDateTime(String str) {
        Date date = null;
        try {
            date = sdfMinute.parse(str);
        } catch (Exception e) {
            Log.i(TAG, "stringToDate: " + e);
        }
        return date;
    }

    /**
     * 格式化创建日期
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getFromatCreateAt(Date date) {
        Calendar base = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        int todayYear = base.get(Calendar.YEAR);
        int todayDayOfYear = base.get(Calendar.DAY_OF_YEAR);
        base.add(Calendar.DAY_OF_YEAR, -1);
        int yesterdayYear = base.get(Calendar.YEAR);
        int yesterdayDayOfYear = base.get(Calendar.DAY_OF_YEAR);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        cal.setTime(date);
        int calYear = cal.get(Calendar.YEAR);
        int calDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        String text;
        if (todayYear == calYear && todayDayOfYear == calDayOfYear) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            text = formatter.format(cal.getTime());
        } else if (yesterdayYear == calYear && yesterdayDayOfYear == calDayOfYear) {
            SimpleDateFormat formatter = new SimpleDateFormat("昨天 HH:mm");
            text = formatter.format(cal.getTime());
        } else if (todayYear == calYear) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
            text = formatter.format(cal.getTime());
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            text = formatter.format(cal.getTime());
        }
        return text;
    }
}
