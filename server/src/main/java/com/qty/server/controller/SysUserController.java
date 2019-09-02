package com.qty.server.controller;

import com.google.common.collect.Maps;
import com.qty.common.response.BaseResponse;
import com.qty.common.response.StatusCode;
import com.qty.model.entity.SysUserEntity;
import com.qty.server.shiro.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public BaseResponse currInfo(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object>resMap= Maps.newHashMap();
        try{
            //拿到当前登录的用户实体
            SysUserEntity userEntity= (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
            resMap.put("user",userEntity);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail);
        }
        response.setData(resMap);
        return response;
    }
}
