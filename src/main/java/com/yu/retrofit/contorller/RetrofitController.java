package com.yu.retrofit.contorller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.yu.retrofit.service.RetrofitService;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author yushijun
 * @date 2019/5/27
 * @description
 */
@RestController("")
@RequestMapping(value = "/retrofit")
@Slf4j
public class RetrofitController {

    @Autowired
    RetrofitService retrofitService;

    @GetMapping("/get/{name}")
    public String get(@PathVariable(value = "name") String name) throws IOException {
        return JSON.toJSONString(retrofitService.get(name).execute().body());
    }

    @PostMapping("/post")
    public String post(@RequestBody Map<String, Object> x) throws IOException {

        log.info("测试成功:{}", x);
        return JSON.toJSONString(retrofitService.post(x).execute().body());
    }

    @PostMapping("/post/{name}")
    public String query(@PathVariable(value = "name") String name) throws IOException {
        return JSON.toJSONString(retrofitService.query(name).execute().body());
    }
    
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") String id) throws IOException {
        return JSON.toJSONString(retrofitService.delete(id).execute().body());
    }

    @PutMapping("/put/{id}")
    public String put(@PathVariable(value = "id") String id) throws IOException {
        return JSON.toJSONString(retrofitService.put(id).execute().body());
    }

    @PostMapping("/async")
    public String async(@RequestBody Map<String, Object> x) throws IOException {

        log.info("测试成功:{}", x);
        retrofitService.post(x).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful()) {
                    log.info("异步请求成功结果：{}", JSON.toJSONString(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                log.info("", t);
            }
        });
        log.info("async执行请求结束");
        return "{}";
    }

    @PostMapping("/upload")
    public String upload(@RequestBody Map<String, String> x) throws IOException {

        File file = new File(x.get("filePath"));

        okhttp3.RequestBody fileRQ = okhttp3.RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRQ);

        return retrofitService.upload(part).execute().body().toString();

    }

    @PostMapping("/form")
    public String form(@RequestParam(value = "name") String name, @RequestParam(value = "userId") String userId)
            throws IOException {
        Response<ResponseBody> execute = retrofitService.form(ImmutableMap.of("name", name, "userId", userId))
                .execute();
        // 非200 在errorBody中
        if (!execute.isSuccessful()) {
            return execute.errorBody().string();
        } else {
            return JSON.toJSONString(execute.body());
        }
    }

    @PostMapping("/queryMap")
    public String queryMap(@RequestBody Map<String, Object> queryMap) throws IOException {
        return JSON.toJSONString(retrofitService.queryMap(queryMap).execute().body());
    }

    @PostMapping("/mutil/{path}")
    public String mutil(@PathVariable("path") String path, @RequestParam("param") String param,
            @RequestBody Map<String, Object> queryMap) throws IOException {
        return JSON.toJSONString(retrofitService.mutil(path, param, queryMap).execute().body());
    }

}
