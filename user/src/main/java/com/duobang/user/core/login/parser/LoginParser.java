package com.duobang.user.core.login.parser;

import com.alibaba.fastjson.JSONObject;

public class LoginParser {

    /**
     * 获得账号登录的body
     * @param username 用户名
     * @param password 密码
     * @return json
     */
    public static String getAccountLoginBody(String username, String password) throws NullPointerException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获得手机号登录的body
     * @param phone 手机号
     * @param captcha 验证码
     * @return json
     */
    public static String getPhoneLoginBody(String phone, String captcha) throws NullPointerException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            jsonObject.put("captcha", captcha);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *  获取注册的body
     * @param username 用户名
     * @param password 密码
     * @param phone    手机号
     * @param nickname 昵称
     * @return json
     */
    public static String getRegisterBody(String username, String password, String phone, String nickname) throws NullPointerException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("phone", phone);
            jsonObject.put("nickname", nickname);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            return null;
        }
    }
}
