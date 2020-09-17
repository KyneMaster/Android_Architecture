package com.duobang.pms_lib.utils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Kyne
 * @// 2019/4/20 JSON工具类
 */
public class JsonUtil<T> {

    public static <T> T toObj(String json, Class<T> clazz) throws IllegalStateException {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> ArrayList<T> toList(String json, Class<T> clazz) throws JSONException {
        Gson gson = new Gson();
        ArrayList<T> list = new ArrayList<T>();
        JSONArray jArr = new JSONArray(json);
        for (int i = 0; i < jArr.length(); i++) {
            T t = gson.fromJson(jArr.getString(i), clazz);
            list.add(t);
        }
        return list;
    }

    public static HashMap<Integer, Integer> toMap(String json) {
        if (json == null) {
            return null;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            for (int j = 0; j < jsonObject.length(); j++) {
                Iterator<String> iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = jsonObject.get(key).toString();
                    map.put(Integer.parseInt(key), Integer.parseInt(value));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

}
