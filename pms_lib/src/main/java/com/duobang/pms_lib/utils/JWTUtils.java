package com.duobang.pms_lib.utils;

import android.util.Base64;

/**
 * Created by kyne on 19-12-28.
 */

public class JWTUtils {
    private static final String TAG = "JWTUtils";

    public static String getDecodedJwt(String jwt) {
        String result = "";

        String[] parts = jwt.split("[.]");
        try {

            int index = 0;
            for (String part : parts) {
                if (index == 1){
                    part = part.replaceAll("_", "/");
                    part = part.replaceAll("-", "+");
                    String decodedPart = new String(Base64.decode(part.getBytes("UTF-8"), Base64.DEFAULT));
                    result += decodedPart;
                }
                index++;
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not decode jwt", e);
        }
        return result;
    }

}
