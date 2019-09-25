package com.chen.concise.example01.http.builder;

import com.chen.concise.example01.http.request.PostFileRequest;
import com.chen.concise.example01.http.request.RequestCall;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 *
 */
public class PostFileBuilder extends  BaseRequestBuilder<PostFileBuilder> implements HasParamsable
{
    private File file=null;
    private MediaType mediaType=null;
    private String fileParams=null;

    public PostFileBuilder fileAndParams(String fileParams,File file)
    {
        this.fileParams=fileParams;
        this.file = file;
        return this;
    }
    public PostFileBuilder file(File file)
    {
        this.file = file;
        return this;
    }

    public PostFileBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostFileRequest(url, tag, params, headers, file,fileParams, mediaType,id).build();
    }


    @Override
    public PostFileBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostFileBuilder addParams(String key, String val) {
        if (this.params == null)
        {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }
}
