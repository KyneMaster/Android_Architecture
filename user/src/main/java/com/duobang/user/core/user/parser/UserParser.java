package com.duobang.user.core.user.parser;

import com.alibaba.fastjson.JSONObject;

public class UserParser {

    /**
     * 修改头像body
     *
     * @param userId
     * @param avatar
     * @return
     */
    public static String getUserAvatarBody(String userId, String avatar){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("avatar", avatar);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 修改昵称body
     *
     * @param userId
     * @param nickname
     * @return
     */
    public static String getNickNameBody(String userId, String nickname){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("nickname", nickname);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 修改密码body
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public static String getPasswordBody(String userId, String oldPassword, String newPassword){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("oldPassword", oldPassword);
            jsonObject.put("newPassword", newPassword);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证码修改密码body
     *
     * @param captcha
     * @param newPassword
     * @return
     */
    public static String getPhonePasswordBody(String captcha, String newPassword){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("captcha", captcha);
            jsonObject.put("newPassword", newPassword);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            return null;
        }
    }
}
