package com.chen.concise.log.help;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
//import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author AlienChao
 * date 2019/08/05 16:18
 */
public class InterceptorUtil {
//    public static String TAG="jsc";
//    //日志拦截器
//    public static HttpLoggingInterceptor LogInterceptor(){
//        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Log.w(TAG, "log: "+message );
//            }
//        }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
//    }
//
//    public static Interceptor HeaderInterceptor(){
//        return   new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request mRequest=chain.request();
//                //在这里你可以做一些想做的事,比如token失效时,重新获取token
//                //或者添加header等等,PS我会在下一篇文章总写拦截token方法
//                return chain.proceed(mRequest);
//
////                String token = MainDataManager.getInstance().getToken();
////                if (TextUtils.isEmpty(token)) {
////                    Request originalRequest = chain.request();
////                    return chain.proceed(originalRequest);
////                }else {
////                    Request originalRequest = chain.request();
////                    Request updateRequest = originalRequest.newBuilder().header("access_token", token).build();
////                    return chain.proceed(updateRequest);
////                }
//            }
//        };
//
//    }


}
