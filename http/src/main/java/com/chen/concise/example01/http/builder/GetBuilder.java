package com.chen.concise.example01.http.builder;

import android.net.Uri;

import com.chen.concise.example01.http.request.GetRequest;
import com.chen.concise.example01.http.request.RequestCall;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by chenyongan
 */
public class GetBuilder  extends BaseRequestBuilder<GetBuilder>  implements HasParamsable {
    @Override
    public RequestCall build() {

        if (params != null)
        {
//            ConciseManager.Builders builders = ConciseManager.getBuilders();
//            String appname = builders.getAppname();
//            String appversion = builders.getAppversion();
//            String device = builders.getDevice();
//            String token = builders.getToken();
//            params.put("appname",appname);
//            params.put("version",appversion);
//            params.put("device",device);
//            params.put("token",token);
            url = appendParams(url, params);
        }
        else{
            params=new LinkedHashMap<>();
//            ConciseManager.Builders builders = ConciseManager.getBuilders();
//            String appname = builders.getAppname();
//            String appversion = builders.getAppversion();
//            String device = builders.getDevice();
//            String token = builders.getToken();
//            params.put("appname",appname);
//            params.put("version",appversion);
//            params.put("device",device);
//            params.put("token",token);
            url = appendParams(url, params);
        }
        return new GetRequest(url,tag,params,headers,id).build();
    }
    protected String appendParams(String url, Map<String, String> params)
    {
        if (url == null || params == null || params.isEmpty())
        {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }
    @Override
    public GetBuilder params(Map<String, String> params) {
        this.params=params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if(this.params==null){
            params=new LinkedHashMap<>();
        }
        params.put(key,val);
        return this;
    }
}
