package com.duobang.pms_lib.utils;

public class MathUtil {

    public static double log(double base, double fiducialValue, double value) {
        return Math.log(value) / Math.log(base) + fiducialValue;
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String value) {
        try {
            if (value.substring(value.length() - 1).equals(".")) {
                return false;
            }
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    public static String secondToMinutesAndSeconds(String second) {
        StringBuffer sb = new StringBuffer();
        if (isNumber(second)) {
            Double seconds = Double.parseDouble(second);
            if (seconds >= 3600) {
                sb.append(String.valueOf((int) (seconds / 3600)));
                sb.append("小时");
            }
            if (seconds >= 60 && ((int) (seconds % 3600) / 60) != 0) {
                sb.append(String.valueOf((int) (seconds % 3600) / 60));
                sb.append("分钟");
            }
            if (seconds > 0 && ((int) (seconds % 3600) % 60) != 0) {
                sb.append(String.valueOf((int) (seconds % 3600) % 60));
                sb.append("秒");
            }
        }
        return sb.toString();
    }
}
