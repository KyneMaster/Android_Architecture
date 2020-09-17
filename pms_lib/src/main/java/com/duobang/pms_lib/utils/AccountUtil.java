package com.duobang.pms_lib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountUtil {
    public static boolean isEmail(String account) {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(account);
        return matcher.matches();
    }

    public static boolean isPhone(String account) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher(account);
        return matcher.matches() && account.length() == 11;
    }
}
