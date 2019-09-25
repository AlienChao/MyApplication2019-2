package com.chen.concise.example01.http.request;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by chenyongan
 */
public class PutRequest extends BaseRequest {

    public PutRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
        super(url, tag, params, headers, id);

    }

    @Override
    protected RequestBody buildRequestBody() {
        if(params!=null) {
            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(params).toString());
        }
        else{
            return null;
        }
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.put(requestBody).build();
    }
}
