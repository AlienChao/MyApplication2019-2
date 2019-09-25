package com.chen.concise.example01.http.callback;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * Created by chenyongan
 */
public abstract class JsonCallBack extends AbCallBack<JSONObject> {
    @Override
    public JSONObject parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        JSONObject jsonObject = new JSONObject(string);
        return jsonObject;
    }
}
