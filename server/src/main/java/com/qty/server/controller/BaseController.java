package com.qty.server.controller;

import com.qty.common.response.BaseResponse;
import com.qty.common.response.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
public class BaseController {

    /**
     * 第一个案例-json格式响应体交互
     * @param name
     * @return
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public BaseResponse info(String name){
        BaseResponse response=new BaseResponse(StatusCode.Success);

        if (StringUtils.isBlank(name)){
            name="权限管理平台!";
        }
        response.setData(name);

        return response;
    }
}
