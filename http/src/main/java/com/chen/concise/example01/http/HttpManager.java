package com.chen.concise.example01.http;

import com.chen.concise.example01.http.builder.GetBuilder;
import com.chen.concise.example01.http.builder.PostFileBuilder;
import com.chen.concise.example01.http.builder.PostFormBuilder;
import com.chen.concise.example01.http.builder.PutBuilder;
import com.chen.concise.example01.http.callback.AbCallBack;
import com.chen.concise.example01.http.request.RequestCall;
import com.chen.concise.example01.utils.SchedulerThread;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by chenyongan
 */
public class HttpManager {

    private static volatile HttpManager httpUtils = null;

    private OkHttpClient mOkHttpClient = null;

    private SchedulerThread mSchedulerThread;

    public HttpManager(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS).build();
        } else {
            mOkHttpClient = okHttpClient;
        }
        mSchedulerThread = SchedulerThread.get();
    }

    /**
     * The singleton pattern
     *
     * return
     */
    public static HttpManager getInstance() {
        return init(null);
    }

    public static HttpManager init(OkHttpClient mOkHttpClient) {
        if (httpUtils == null) {
            synchronized (HttpManager.class) {
                httpUtils = new HttpManager(mOkHttpClient);
            }
        }
        return httpUtils;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void execute(RequestCall requestCall, AbCallBack callBack) {
        if (callBack == null) {
            callBack = AbCallBack.DEFATLT_CALLBACK;
        }
        final AbCallBack mcallback = callBack;
        if(requestCall==null){
            throw new NullPointerException("requestcall is null");
        }
        final int id = requestCall.getBaseRequest().getId();
        if(requestCall.getCall()==null){
            requestCall.newCall(callBack);
        }
        requestCall.getCall().enqueue(
                    new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            sendFailResultCallback(call, e, mcallback, id);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (call.isCanceled()) {
                                sendFailResultCallback(call, new IOException("cancle request"), mcallback, id);
                            }
                            if (!mcallback.validateReponse(response, id)) {
                                sendFailResultCallback(call, new IOException("" + response.code()), mcallback, id);
                                if (response.body() != null) {
                                    response.body().close();
                                }
                                return;
                            }
                            try {
                                Object o = mcallback.parseNetworkResponse(response, id);
                                sendSuccessResultCallback(o, mcallback, id);
                            } catch (Exception e) {
                                sendFailResultCallback(call, e, mcallback, id);
                            } finally {
                                if (response.body() != null) {
                                    response.body().close();
                                }
                            }
                        }
                    }
            );
    }
//


    public void execu(){
//        OkHttpClient okHttpClient=new OkHttpClient().newBuilder().build();
//        Request request=new Request.Builder().url("").get().build();
//        Request request1=new Request.Builder().

    }
    /**
     * Send  a Failed
     *
     * param call
     * param e
     * param callback
     * param id
     */
    public void sendFailResultCallback(final Call call, final Exception e, final AbCallBack callback, final int id) {
        if (callback == null) return;

        mSchedulerThread.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    /**
     * Send a success
     *
     * param object
     * param callback
     * param id
     */
    public void sendSuccessResultCallback(final Object object, final AbCallBack callback, final int id) {
        if (callback == null) return;
        mSchedulerThread.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    public Executor getDelivery()
    {
        return mSchedulerThread.defaultCallbackExecutor();
    }
    public static GetBuilder get(){
        return new GetBuilder();
    }
    public static PutBuilder put(){
        return new PutBuilder();
    }

    public static PostFormBuilder postFormBuilder(){
        return new PostFormBuilder();
    }
    public static PostFileBuilder postFile()
    {
        return new PostFileBuilder();
    }
}
