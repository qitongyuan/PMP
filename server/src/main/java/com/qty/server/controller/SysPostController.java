package com.qty.server.controller;

import com.google.common.collect.Maps;
import com.qty.common.response.BaseResponse;
import com.qty.common.response.StatusCode;
import com.qty.common.utils.PageUtil;
import com.qty.common.utils.ValidatorUtil;
import com.qty.model.entity.SysPostEntity;
import com.qty.server.annotation.LogAnnotation;
import com.qty.server.service.SysPostService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
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

    @LogAnnotation("新增岗位")
    @RequestMapping(value = "/save",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse save(@RequestBody @Validated SysPostEntity entity, BindingResult result){
        String res= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try{
            log.info("新岗位的数据：{}",entity);
            sysPostService.save(entity);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 获取岗位详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/info/{id}")
    public BaseResponse info(@PathVariable Long id){
        if (id==null||id<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object>resMap= Maps.newHashMap();
        try{
            log.info("岗位详情数据: {}",id);
            resMap.put("post",sysPostService.getById(id));
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        response.setData(resMap);
        return  response;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse update(@RequestBody @Validated SysPostEntity entity,BindingResult result){
        String res=ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        if (entity.getPostId()==null||entity.getPostId()<0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try{
            log.info("更新岗位数据");
            sysPostService.updatePost(entity);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
