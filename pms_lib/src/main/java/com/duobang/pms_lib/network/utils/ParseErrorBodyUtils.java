package com.duobang.pms_lib.network.utils;

import com.duobang.pms_lib.network.model.ErrorBody;
import com.duobang.pms_lib.utils.JsonUtil;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author Kyne
 * @// 2019/8/20 返回结果错误 解析message
 */
public class ParseErrorBodyUtils {

    public static String parseErrorBody(Response<ResponseBody> responses) {
        String message = "加载失败！请重试！！";
        try {
            if (responses.errorBody() != null) {
                String body = responses.errorBody().string();
                if (body != null && !body.equals("")) {
                    ErrorBody errorBody = JsonUtil.toObj(body, ErrorBody.class);
                    message = errorBody.getMessage();
                }
            }
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
