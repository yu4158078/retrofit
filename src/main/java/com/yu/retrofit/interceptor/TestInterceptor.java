package com.yu.retrofit.interceptor;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author yushijun
 * @date 2019/5/27
 * @description
 */
@Slf4j
public class TestInterceptor  implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        log.info("请求方式：{}",chain.request());

        //可以做一些通用的处理：加解密、header、auth认证
        return chain.proceed(chain.request());
    }
}
