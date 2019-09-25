package com.chen.concise.example01.http.builder;

import com.chen.concise.example01.http.request.PostRequest;
import com.chen.concise.example01.http.request.RequestCall;

import okhttp3.MediaType;

/**
 * Created by   chenyongan
 */
public class PostBuilder extends BaseRequestBuilder<PostBuilder> {
    private String content;
    private MediaType mediaType;
    public PostBuilder content(String content){
        this.content=content;
        return this;
    }
    public PostBuilder mediaType(MediaType mediaType){
        this.mediaType=mediaType;
        return this;
    }
    @Override
    public RequestCall build() {
        return new PostRequest(url, tag, params, headers, content, mediaType,id).build();
    }
}
