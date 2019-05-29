package com.yu.retrofit.contorller;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;

/**
 * @author yushijun
 * @date 2019/5/28
 * @description
 */
@RestController
@RequestMapping("/server")
public class HttpServerController {

    @PostMapping("/post")
    public String post(@RequestBody Map<String, Object> x) throws IOException {
        return JSON.toJSONString(x);
    }

    @GetMapping("/get/{name}")
    public String get(@PathVariable(value = "name") String name) throws IOException {
        return JSON.toJSONString(ImmutableMap.of("name", name));
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") String id) throws IOException {
        return JSON.toJSONString(ImmutableMap.of("id", id));
    }

    @PutMapping("/put/{id}")
    public String put(@PathVariable(value = "id") String id) throws IOException {
        return JSON.toJSONString(ImmutableMap.of("id", id));
    }

    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return file.getOriginalFilename();
    }

}
