package com.cvte.notesync.utils;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

    public static JSONObject strToJson(String text) {
        return JSONObject.parseObject(text);
    }

    public static String parseJson(Object obj) {
        return JSONObject.toJSONString(obj);
    }
}
