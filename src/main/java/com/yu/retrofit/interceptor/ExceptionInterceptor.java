package com.yu.retrofit.interceptor;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yushijun
 * @date 2019/4/24
 * @description
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class ExceptionInterceptor {
    @org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class })
    public String handleException(Exception e) {
        log.error("请求失败---", e);
        if (e instanceof IOException) {
            log.info("IO异常", e);
            return e.getMessage();
        } else {
            log.info("请求Exception异常", e);
            return e.getMessage();
        }
    }
}
