package com.chen.concise.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.RequestBody;
import okio.Buffer;

/**
 * @author AlienChao
 * date 2019/08/21 17:50
 */
public class JsonToMap {


    /**
     * 依据json字符串返回Map对象
     *
     * param json
     * return
     */
    public static Map<String, Object> toMap(String json) {
        return toMap(parseJson(json));
    }

    public static JsonObject parseJson(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        return jsonObj;
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     *
     * param json
     * return
     */
    public static Map<String, Object> toMap(JsonObject json) {
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        for (Iterator<Map.Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ) {
            Map.Entry<String, JsonElement> entry = iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            map.put(key,value);
        }
        return map;
    }





}
