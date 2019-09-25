package com.chen.concise;

import androidx.annotation.NonNull;

import com.chen.concise.api.LoginService;
import com.chen.concise.log.help.InterceptorUtil;
import com.chen.concise.log.RequestInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 方便调度类
 *
 * @author AlienChao
 * date 2019/08/05 09:48
 */
public class RxHttp {


    private static String BaseUrl = "http://222.169.192.232:6888/platform/servlet/OutAjaxServlet/";


    private Map<String, Retrofit> retrofitMap = new HashMap();

    public static RxHttp getInstance() {
        return RxHttpHolder.mRxHttp;
    }

    public static class RxHttpHolder {
        private final static RxHttp mRxHttp = new RxHttp();
    }


    public LoginService getSysService() {
        return getRetrofit(BaseUrl).create(LoginService.class);
    }

    public static LoginService getDefaultService() {
        return getInstance().getRetrofit(BaseUrl).create(LoginService.class);
    }


    /**
     * 获取ServiceApi
     *
     * param cache
     * param <T>
     * return
     */
    public static <T> T getRetrofitService(@NonNull Class<T> cache) {
        return getInstance().getRetrofit(BaseUrl).create(cache);
    }




    /**
     * 根据BaseUrl得到 Retrofit
     *
     * param serviceUrl
     * return
     */
    public Retrofit getRetrofit(String serviceUrl) {
        Retrofit retrofit;
        if (retrofitMap.containsKey(serviceUrl)) {
            retrofit = retrofitMap.get(serviceUrl);
        } else {
            retrofit = createRetrofit(serviceUrl);
            retrofitMap.put(serviceUrl, retrofit);
        }
        return retrofit;
    }


    /**
     * 创建 Retrofit
     *
     * param baseUrl
     * return
     */
    private Retrofit createRetrofit(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                //  .addInterceptor(InterceptorUtil.LogInterceptor())
                .addInterceptor(LotteryOAInterceptor.getInstance())//添加其他拦截器
                .addInterceptor(new RequestInterceptor())

                .retryOnConnectionFailure(true)
                .build();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

    }


}
