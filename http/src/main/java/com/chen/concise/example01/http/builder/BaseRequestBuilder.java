package com.chen.concise.example01.http.builder;

import com.chen.concise.example01.http.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by chenyongan
 */
public abstract class BaseRequestBuilder<T extends BaseRequestBuilder> {
    protected String url;
    protected Object tag;
    protected Map<String, String> headers;
    protected Map<String, String> params;
    protected int id;

    public T id(int id)
    {
        this.id = id;
        return (T) this;
    }

    public T url(String url)
    {
        this.url = url;
        return (T) this;
    }


    public T tag(Object tag)
    {
        this.tag = tag;
        return (T) this;
    }

    public T headers(Map<String, String> headers)
    {
        this.headers = headers;
        return (T) this;
    }

    public T addHeader(String key, String val)
    {
        if (this.headers == null)
        {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return (T) this;
    }

    public abstract RequestCall build();
}
