package com.duobang.pms_lib.utils;

public class NumberFormatUtil {

    public static String parseNumber(String number) {
        StringBuilder result = new StringBuilder();
        float num = 0;
        try {
            num = Float.parseFloat(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (num >= 10000 && num < 1000000) {
            num /= 10000;
            result.append(new java.text.DecimalFormat("#.00").format(num));
            result.append("万");
        } else if (num >= 1000000 && num < 10000000) {
            num /= 1000000;
            result.append(new java.text.DecimalFormat("#.00").format(num));
            result.append("百万");
        } else if (num >= 10000000) {
            num /= 10000000;
            result.append(new java.text.DecimalFormat("#.00").format(num));
            result.append("千万");
        } else {
            result.append(number);
        }
        return result.toString();

    }
}
