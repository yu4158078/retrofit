package com.yu.retrofit.service;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author yushijun
 * @date 2019/5/27
 * @description
 */
public interface RetrofitService {

    /**
     * post json
     * @param req
     * @return
     */
    @POST("/server/post")
    Call<Map<String, Object>> post(@Body Map<String, Object> req);


    /**
     * post form 请求
     * @param req
     * @return
     */
    @FormUrlEncoded
    @POST("/server/form")
    Call<ResponseBody> form(@FieldMap Map<String, Object> req);

    /**
     * 上传文件
     * @param body
     * @return
     */
    @Multipart
    @POST("/server/upload")
    Call<ResponseBody> upload(@Part MultipartBody.Part body);

    @GET("/server/get/{name}")
    Call<Map<String, Object>> get(@Path("name") String name);

    @DELETE("/server/delete/{id}")
    Call<Map<String, Object>> delete(@Path("id") String id);

    @PUT("/server/put/{id}")
    Call<Map<String, Object>> put(@Path("id") String id);

    /**
     * 添加header
     * @param req
     * @return
     */
    @Headers(value = "Authorization:QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
    @POST("/server/header")
    Call<Map<String, Object>> header(@Body  Map<String, Object> req);

    /**
     * 添加header
     * @param req
     * @return
     */
    @POST("/server/header1")
    Call<Map<String, Object>> header1(@Header("Authorization") String value,@Body Map<String, Object> req);

    /**
     * /server/queryMap?a=111&b=222
     * 
     * @param req
     * @return
     */
    @POST("/server/queryMap")
    Call<Map<String, Object>> queryMap(@QueryMap Map<String, Object> req);

    /**
     * /server/queryMap?name=req
     * @param req
     * @return
     */
    @POST("/server/query")
    Call<Map<String, Object>> query(@Query("name") String req);

}