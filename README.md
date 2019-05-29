# retrofit demo

|Author|yu|
|---|---
|E-mail|506909808@qq.com

# 使用技术
springboot + retrofit + swegger2
测试地址：http://localhost:8080/doc.html

# Retrofit简介

Retrofit is a REST Client for Java and Android. It makes it relatively easy to retrieve and upload JSON (or other structured data) via a REST based webservice. In Retrofit you configure which converter is used for the data serialization. Typically for JSON you use GSon, but you can add custom converters to process XML or other protocols. Retrofit uses the OkHttp library for HTTP requests.

Retrofit是Java和Android的REST客户端。 它使通过基于REST的webService和JSON（或其他结构化数据）变得相对容易。 在Retrofit中，您可以配置用于数据序列化的转换器。 通常对于JSON使用GSon，但您可以添加自定义转换器来处理XML或其他协议。 Retrofit使用OkHttp库进行HTTP请求。


* retrofit 更类似于 spring 的restTemplate 对已有网络请求libary 再次封装

* retrofit 基于okHttp ，这两个libary 都是Square Open Source 维护，对于已经使用okHttp的项目可以平滑迁移

* retrofit 诞生的意义：把Url 和 http请求解耦、打散，针对接口编程、简化代码、增加易读性

# OkHttp简介

OkHttp 是类似于 jdk HttpUrlConnection、apache httpclient 的网络http client libary，常见于安卓和java平台

支持httpClient 已有的特性，同步请求、异步回调请求、http2.0、https、自动重试等

# retrofit的特性

1. 支持多种反序列化工具：

    To convert to and from JSON:

        Gson: com.squareup.retrofit:converter-gson

        Jackson: com.squareup.retrofit:converter-jackson

        Moshi: com.squareup.retrofit:converter-moshi

    To convert to and from Protocol Buffers:

        Protobuf: com.squareup.retrofit:converter-protobuf

        Wire: com.squareup.retrofit:converter-wire

    To convert to and from XML:

        Simple XML: com.squareup.retrofit:converter-simplexml

2.支持自定义反序列化

3.支持请求适配器（java 平台暂时没用 ，andorid-RxJava）

4.解耦
为什么使用retrofit

1.基于接口编程，解耦，各个http请求之间无关联。修改影响小、容易扩展

2.基于目前平台的现状:都使用不同的http网络请求封装，可读性一般、不方便后期维护


## Retrofit使用
### maven依赖

    <dependency>
     <groupId>com.squareup.retrofit2</groupId>
     <artifactId>retrofit</artifactId>
     <version>2.5.0</version>
    </dependency>
    <dependency>
     <groupId>com.squareup.okhttp3</groupId>
     <artifactId>okhttp</artifactId>
     <version>3.14.1</version>
    </dependency>
    <dependency>
     <groupId>com.squareup.okhttp3</groupId>
     <artifactId>logging-interceptor</artifactId>
     <version>3.14.1</version>
    </dependency>
    <dependency>
     <groupId>com.squareup.retrofit2</groupId>
     <artifactId>converter-jackson</artifactId>
     <version>2.5.0</version>
    </dependency>

### retrofit初始化
    
```Java 
    // Retrofit 依赖okHttp 设置okHttp 参数
    
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    
    OkHttpClient okHttpClient = builder.addInterceptor(new TestInterceptor())
    
    .connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS).readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
    
    .addInterceptor(testInterceptor).addInterceptor(httpLoggingInterceptor).build();
    
    
    // 反序列化Convert
    
    ObjectMapper mapper = new ObjectMapper();
    
    // 过滤对象的null属性.
    
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    
    // 实例化 Retrofit
    
    Retrofit retrofit = new Retrofit.Builder().baseUrl(basicUrl)
    
    .addConverterFactory(JacksonConverterFactory.create(mapper)).client(okHttpClient).build();
```

### Retrofit 注解
	
| 注解代码 | 请求格式  | 
| :------------ |:---------------:| 
| @GET          |GET请求 |
| @POST      | POST请求        |
| @DELETE | DELETE请求        |
|@HEAD | HEAD请求|
|@OPTIONS	|OPTIONS请求|
|@PATCH	| PATCH请求|
|@Headers|Head 头信息|
	

### 常用参数注解

| 注解 | 描述 | 使用 |
| :------------ |:---------------:|:---------------:|  
|@Path |restful 替换路径中{}，和Spring @PathVariable一样 |   ```Java @GET("/server/get/{name}") Call<Map<String, Object>> get(@Path("name") String name);/server/get/name```|
    
| | |   ```Java  ```|

        
    
    
    @Query
    url中拼接参数 在?后
    @POST("/server/query")
    Call<Map<String, Object>> query(@Query("name") String req);/server/queryMap?name=req
    
    @QueryMap：
    url中拼接参数 在?后
    @POST("/server/queryMap")
    
    Call<Map<String, Object>> queryMap(@QueryMap Map<String, Object> req);/server/queryMap?a=111&b=222

    @FormUrlEncoded
    用表单数据提交
    
    @FieldMap
    form请求多参数 和@FormUrlEncoded 一起使用
    
    @Field
    from 请求参数 和@FormUrlEncoded 一起使用
    
    @Multipart
    文件上传 和 @Part 联合使用
    
    @Part
    单文件上传和Spring MutilPart
    
    @PartMap
    多文件上传和Spring List<MutilPart>
	
    
    @Body
        
    
    post json 和spring @RequestBody
        
    
    @POST("/server/post")
    
    Call<Map<String, Object>> header(@Body Map<String, Object> req);
    
    body=req.toJsonString




##相关资料：

[retrofit官方地址](https://square.github.io/retrofit/)

[github地址](https://github.com/square/retrofit)

[retrofit学习视频](https://futurestud.io/tutorials/retrofit-getting-started-and-android-client)

[okhttp官方地址](https://square.github.io/okhttp/)