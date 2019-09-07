package com.qty.server.controller;

import com.google.common.collect.Maps;
import com.qty.common.response.BaseResponse;
import com.qty.common.response.StatusCode;
import com.qty.common.utils.PageUtil;
import com.qty.server.service.SysPostService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SysPostService sysPostService;

    @RequestMapping("/list")
    public BaseResponse list(@RequestParam Map<String,Object>paramMap){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap= Maps.newHashMap();
        try{
            PageUtil page=sysPostService.queryPage(paramMap);
            resMap.put("page",page);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        response.setData(resMap);
        return response;
    }

}
