package com.yu.retrofit.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yu.retrofit.interceptor.TestInterceptor;
import com.yu.retrofit.service.RetrofitService;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author yushijun
 * @date 2019/4/23
 * @description Retrofit 配置类
 */
@Configuration
@Slf4j
public class RetrofitConfig {

    @Value("${basic.url}")
    private String basicUrl;
    // 单位毫秒
    @Value("${http.read.timeout:120000}")
    private Integer readTimeOut;
    @Value("${http.connect.timeout:10000}")
    private Integer connectTimeOut;

    @ConditionalOnMissingBean
    @Bean
    public TestInterceptor testInterceptor() {
        TestInterceptor interceptor = new TestInterceptor();
        return interceptor;
    }

    @ConditionalOnMissingBean
    @Bean
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @ConditionalOnMissingBean
    @ConditionalOnBean({ TestInterceptor.class, HttpLoggingInterceptor.class })
    @Bean
    public Retrofit retrofit(Interceptor testInterceptor, Interceptor httpLoggingInterceptor) {
        // Retrofit 依赖okHttp 设置okHttp 参数
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder.addInterceptor(new TestInterceptor())
                .connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS).readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                .addInterceptor(testInterceptor).addInterceptor(httpLoggingInterceptor).build();

        // 反序列化
        ObjectMapper mapper = new ObjectMapper();
        // 过滤对象的null属性.
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 实例化 Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(basicUrl)
                .addConverterFactory(JacksonConverterFactory.create(mapper)).client(okHttpClient).build();
        log.info("初始化Retrofit-------------------------成功");
        return retrofit;
    }

    @ConditionalOnMissingBean
    @Bean
    public RetrofitService szsService(Retrofit retrofit) {
        return retrofit.create(RetrofitService.class);
    }


}
