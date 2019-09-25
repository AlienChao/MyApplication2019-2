package com.chen.concise.example01.http.callback;

import java.io.IOException;

import okhttp3.Response;


public abstract class StringCallBack extends AbCallBack<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {

        String result = response.body().string();
        return result;
    }
}
