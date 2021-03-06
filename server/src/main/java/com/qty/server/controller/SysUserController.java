package com.qty.server.controller;

import com.google.common.collect.Maps;
import com.qty.common.response.BaseResponse;
import com.qty.common.response.StatusCode;
import com.qty.model.entity.SysUserEntity;
import com.qty.server.service.SysUserService;
import com.qty.server.shiro.ShiroUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;

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

    @RequestMapping("/password")
    public BaseResponse updatePassword(String password,String newPassword){
        if (StringUtils.isBlank(password)||StringUtils.isBlank(newPassword)){
            return new BaseResponse(StatusCode.PasswordCanNotBlank);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try{
            SysUserEntity entity= (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
            //校验原始密码是否正确
            final String salt=entity.getSalt();
            //生成数据库中的加密后密码
            String oldPsd=ShiroUtil.sha256(password,salt);
            if (!entity.getPassword().equals(oldPsd)){
                return new BaseResponse(StatusCode.OldPasswordNotMatch);
            }
            String newPsd=ShiroUtil.sha256(newPassword,salt);
            log.info("验证密码，然后更新");
            sysUserService.updatePassword(entity.getUserId(),oldPsd,newPsd);
        }catch (Exception e){
            //修改密码失败
            response=new BaseResponse(StatusCode.UpdatePasswordFail);
        }
        return  response;
    }
}
