package com.chen.concise.example01.http.callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenyongan
 */
public abstract class AbCallBack<T> {


    /**
     * main thread
     * Before task execution
     * param request
     */
    public void onBefore(Request request, int id)
    {
    }

    /**
     * MainThread
     * After task execution
     * param
     */
    public void onAfter(int id)
    {
    }
    /**
     * UI Thread
     *
     * param progress
     */
    public void inProgress(float progress, long total , int id)
    {
    }
    /**
     * Analytical network data returned
     * param response
     * param id
     * return
     * @throws Exception
     */
    public abstract T parseNetworkResponse(Response response, int id) throws Exception;

    /**
     * If you're parseNetworkResponse analytic response code, you should make this method returns true.
     *
     * param response
     * return
     */
    public boolean validateReponse(Response response, int id)
    {
        return response.isSuccessful();
    }
    /**
     *  on failure
     * param call Call
     * param e
     * param id
     */
    public abstract void onError(Call call, Exception e, int id);

    /**
     * on onResponse
     * param response
     * param id
     */
    public abstract void onResponse(T response, int id);
    public static AbCallBack DEFATLT_CALLBACK=new AbCallBack() {
        @Override
        public Object parseNetworkResponse(Response response, int id) throws Exception {
            return null;
        }

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(Object response, int id) {

        }
    };
}
