package com.chen.concise.example01.http.builder;

import java.util.Map;

/**
 * Created by chenyongan
 */
public interface HasParamsable {
    BaseRequestBuilder params(Map<String,String> params);
    BaseRequestBuilder addParams(String key,String val);
}
