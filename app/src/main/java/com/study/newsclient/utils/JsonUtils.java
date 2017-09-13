package com.study.newsclient.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

/**
 * Created by wyy on 2017/9/6.
 */

public class JsonUtils {
    /**
     * 对象转成json字符串
     * @param obj
     * @return
     */
    public static String serialized(Object obj) {
        if (obj != null)
            return new Gson().toJson(obj);
        else {
            throw new RuntimeException("对象不能为空");
        }
    }


    /**
     * json字符串转成对象
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T deSerializedByType(String json, Type type) {
        if (TextUtils.isEmpty(json) && json != null) {
            throw new RuntimeException("json is null");
        }
        JsonElement jsonElement = new JsonParser().parse(json);
        if (jsonElement.isJsonNull()) {
            throw new RuntimeException("json is null");
        }
        if (!jsonElement.isJsonObject()) {
            throw new RuntimeException("json not object");
        }
        return new Gson().fromJson(jsonElement, type);
    }
}
