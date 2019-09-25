package com.chen.concise.api;

import com.chen.concise.Blog;
import com.chen.concise.Response;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author AlienChao
 * date 2019/08/05 09:51
 */
public interface LoginService {

//Observable<Response<Blog>> createBlog(@Body Blog blog);


    @GET("blog/{id}") //这里的{id} 表示是一个变量
    Observable<ResponseBody> getBlog(@Path("id") int id);


    @POST("blog")
    Observable<Response<Blog>> getlogin(@Body Blog blog);

    @GET("https://www.baidu.com/")
    Observable<ResponseBody> getBaidu();

}
