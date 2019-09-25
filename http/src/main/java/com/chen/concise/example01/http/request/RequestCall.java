package com.chen.concise.example01.http.request;

import com.chen.concise.example01.http.HttpManager;
import com.chen.concise.example01.http.callback.AbCallBack;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by chenyongan
 */
public class RequestCall {

    private OkHttpClient clone=null;

    private BaseRequest baseRequest=null;

    private  Request request=null;

    private Call call=null;

    public RequestCall(BaseRequest baseRequest){
        this.baseRequest=baseRequest;
    }

    public Call newCall(AbCallBack callback){
        request = generateRequest(callback);
        clone= HttpManager.getInstance().getOkHttpClient().newBuilder().build();
        call = clone.newCall(request);
        return call;
    }

    private Request generateRequest(AbCallBack callback)
    {
        return baseRequest.generateRequest(callback);
    }

    public OkHttpClient getClone() {
        return clone;
    }

    public BaseRequest getBaseRequest() {
        return baseRequest;
    }

    public Request getRequest() {
        return request;
    }

    public void execute(AbCallBack callback)
    {
        newCall(callback);
        if (callback != null)
        {
            callback.onBefore(request, getBaseRequest().getId());
        }
        HttpManager.getInstance().execute(this, callback);
    }

    public Call getCall() {
        return call;
    }

    /**
     * cancle request
     */
    public void cancle(){
        if(call!=null){
            call.cancel();
        }
    }
}
