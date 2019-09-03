package com.qty.server.controller;

import com.google.common.collect.Maps;
import com.qty.common.response.BaseResponse;
import com.qty.common.response.StatusCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 岗位增删改
 */
@RestController
@RequestMapping("/sys/post")
public class SysPostController extends AbstractController {

    @RequestMapping("/list")
    public BaseResponse list(@RequestParam Map<String,Object>paramMap){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap= Maps.newHashMap();

        return null;
    }

}
