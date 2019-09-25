package com.chen.concise.example01.http.request;

import android.util.Log;

import com.chen.concise.example01.http.callback.AbCallBack;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Request the base class for class
 * Created by chenyongan
 */
public abstract class BaseRequest {
    protected String url;
    protected Object tag;
    protected int id;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected Request.Builder builder = new Request.Builder();

    protected BaseRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
        this.url = url;
        this.tag = tag;
//        ConciseManager.Builders builders = ConciseManager.getBuilders();
//        String appname = builders.getAppname();
//        String appversion = builders.getAppversion();
//        String device = builders.getDevice();
//        String token = builders.getToken();
        if(params==null){
            params=new HashMap<>();
        }
//        params.put("appname",appname);
//        params.put("version",appversion);
//        params.put("device",device);
//        params.put("token",token);
        this.params = params;
        this.headers = headers;



        this.id = id;
        if (url == null) {
            throw new NullPointerException("url not null");
        }
        init();
    }

    protected abstract RequestBody buildRequestBody();

    protected abstract Request buildRequest(RequestBody requestBody);

    public RequestBody wrapRequestBody(RequestBody requestBody, AbCallBack callback) {
        return requestBody;
    }

    /**
     * create a requestcall
     *
     * return RequestCall
     */
    public RequestCall build() {

        return new RequestCall(this);
    }

    public Request generateRequest(AbCallBack callback) {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        Log.i("concise", request.method()+" url:"+url+" params: "+params);
        return request;
    }

    /**
     * Initialize the
     */
    protected void init() {
        this.builder.url(url).tag(tag);
        addHeader();
    }

    /**
     * Add the header information
     */
    protected void addHeader() {

        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;
        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());

    }

    public int getId() {
        return id;
    }
}
