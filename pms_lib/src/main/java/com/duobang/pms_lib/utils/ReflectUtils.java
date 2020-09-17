package com.duobang.pms_lib.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {

    /**
     * 根据属性名获取属性值——<Method> 声明的get方法
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据属性名获取属性值——<Field> 声明的属性(字段)
     */
    public static Object getFieldValue(String fieldName, Object o) {
        try {
            if (isExistField(fieldName, o)) {
                Field field = o.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(o);
            }
        } catch (Exception ignored) {

        }
        return null;
    }

    /**
     * 判断一个类是否存在某个属性
     *
     * @param field 字段
     * @param obj   类对象
     * @return true:存在，false:不存在
     */
    private static Boolean isExistField(String field, Object obj) {
        if (obj == null || EmptyUtils.isEmpty(field)) {
            return false;
        }
        Object o = JSON.toJSON(obj);
        JSONObject jsonObj = new JSONObject();
        if (o instanceof JSONObject) {
            jsonObj = (JSONObject) o;
        }
        return jsonObj.containsKey(field);
    }

    /**
     * 通过JsonObject根据key获取value
     *
     * @param field
     * @param obj
     * @return
     */
    public static Object getValue(String field, Object obj) {
        Object o = JSON.toJSON(obj);
        JSONObject jsonObj = new JSONObject();
        if (o instanceof JSONObject) {
            jsonObj = (JSONObject) o;
        }
        if (jsonObj.containsKey(field)) {
            return jsonObj.get(field);
        }
        return null;
    }
}
