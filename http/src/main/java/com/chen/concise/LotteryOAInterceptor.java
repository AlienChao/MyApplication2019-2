package com.chen.concise;


import com.chen.concise.log.help.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * @author MingPeng
 * Created on 2019/8/15
 */
public class LotteryOAInterceptor implements Interceptor {

    private String staffNo;
    private String authToken;
    private static LotteryOAInterceptor mLotteryOAInterceptor;

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    private LotteryOAInterceptor() {

    }

    public static LotteryOAInterceptor getInstance() {
        if (null == mLotteryOAInterceptor) {
            mLotteryOAInterceptor = new LotteryOAInterceptor();
        }
        return mLotteryOAInterceptor;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Gson gson = new Gson();
        //获取到request
        Request request = chain.request();

        //获取到方法
        String method = request.method();

        //公共参数hasmap

        try {
            //post请求的封装
            if (method.equals("POST")) {
                HttpUrl httpUrl = request.url();
                LogUtils.debugInfo("httpUrl: " + httpUrl.toString());
                RequestBody requestBody = request.body();

                //body 的拆分
                String bodyToString = requestBodyToString(requestBody);
                Map paramsMap = gson.fromJson(bodyToString, Map.class);
                if (null == paramsMap) {
                    paramsMap = new HashMap();
                }

                //添加接口公共参数
                paramsMap.put("staffNo", staffNo);
                paramsMap.put("authToken", authToken);

                //通过请求地址获取path列表
                List<String> pathSegments = httpUrl.pathSegments();
                //取最后一个,为方法名
                String methodName = pathSegments.get(pathSegments.size() - 1);
                //通过请求地址(最初始的请求地址)获取到参数列表
                Set<String> parameterNames = httpUrl.queryParameterNames();
                //循环参数列表,添加至map中
                for (String key : parameterNames) {
                    paramsMap.put(key, httpUrl.queryParameter(key));
                }

                JsonObject params = new JsonObject();
                params.add("params", gson.toJsonTree(paramsMap));
                //组装新的参数
                HashMap<String, Object> map = new HashMap<>();

                map.put("SERVICE_NAME", "appMobileAction." + methodName);
                map.put("SERVICE_TYPE", 0);
                map.put("OPEN_TRANS", 0);
                map.put("PARAMETERS", params);

                //装换成json字符串
                String newJsonParams = gson.toJson(map);

                request = request.newBuilder()
                        .url(httpUrl.toString().substring(0, 59))
                        .post(RequestBody.create(MediaType.parse("application/json"), newJsonParams))
                        .build();
                LogUtils.debugInfo("requestBody: " + newJsonParams);

            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        Response response = chain.proceed(request);
        // 输出返回结果
        try {
            Charset charset;
            charset = Charset.forName("UTF-8");
            ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
            Reader jsonReader = new InputStreamReader(responseBody.byteStream(), charset);
            BufferedReader reader = new BufferedReader(jsonReader);
            StringBuilder sbJson = new StringBuilder();
            String line = reader.readLine();
            do {
                sbJson.append(line);
                line = reader.readLine();
            } while (line != null);
            LogUtils.debugInfo("response: " + sbJson.toString());

        } catch (
                Exception e) {
            e.printStackTrace();
            LogUtils.debugInfo(e.getMessage());
        }
        return response;

    }

    private static String requestBodyToString(RequestBody requestBody) {
        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.readUtf8();
    }


}
