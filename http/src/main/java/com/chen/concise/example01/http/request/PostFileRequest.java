package com.chen.concise.example01.http.request;


import com.chen.concise.example01.http.HttpManager;
import com.chen.concise.example01.http.callback.AbCallBack;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhy on 15/12/14.
 */
public class PostFileRequest extends BaseRequest
{
    private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("image/jpg");

    private File file;
    private MediaType mediaType;
    private String fileparams;
    public PostFileRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, File file,String fileparams, MediaType mediaType, int id)
    {
        super(url, tag, params, headers,id);
        this.file = file;
        this.mediaType = mediaType;
        this.fileparams=fileparams;
        if(this.fileparams==null){
            this.fileparams="image";
        }
        if (this.file == null)
        {
            throw  new NullPointerException("the file can not be null !");
        }
        if (this.mediaType == null)
        {
            this.mediaType = MEDIA_TYPE_STREAM;
        }
    }

    @Override
    protected RequestBody buildRequestBody()
    {
        RequestBody requestBody=MultipartBody.create(mediaType, file);

        MultipartBody.Builder image = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(this.fileparams, "test.jpg", requestBody);
        addParams(image);
        RequestBody build = image.build();
        return  build;
    }

    @Override
    public RequestBody wrapRequestBody(RequestBody requestBody, final AbCallBack callback)
    {
        if (callback == null) return requestBody;
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener()
        {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength)
            {

                HttpManager.getInstance().getDelivery().execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        callback.inProgress(bytesWritten * 1.0f / contentLength,contentLength,id);
                    }
                });

            }
        });
        return countingRequestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody)
    {
        return builder.post(requestBody).build();
    }

    private void addParams(MultipartBody.Builder requestBody)
    {
        if (params != null)
        {
            for (String key : params.keySet())
            {
                requestBody.addFormDataPart(key, params.get(key));
            }
        }
    }

}
