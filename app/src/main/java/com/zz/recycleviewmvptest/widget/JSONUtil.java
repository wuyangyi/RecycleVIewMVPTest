package com.zz.recycleviewmvptest.widget;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author: wuyangyi
 * date: 2019-09-18
 * 解析JSONObject工具类
 */
public class JSONUtil {

    public static String getStringForJSONObject(JSONObject jsonObject, String name, String emptyValue) {
        String realValue = emptyValue;
        try {
            if (jsonObject.has(name)) {
                realValue = jsonObject.getString(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return realValue;
    }

    public static boolean getBooleanForJSONObject(JSONObject jsonObject, String name, boolean emptyValue) {
        boolean realValue = emptyValue;
        try {
            if (jsonObject.has(name)) {
                realValue = jsonObject.getBoolean(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return realValue;
    }
}
