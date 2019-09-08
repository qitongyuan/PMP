package com.qty.server.controller;

import com.google.common.collect.Maps;
import com.qty.common.response.BaseResponse;
import com.qty.common.response.StatusCode;
import com.qty.model.entity.SysDeptEntity;
import com.qty.server.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {

    @Autowired
    private SysDeptService sysDeptService;

    //获取一级部门的/顶级部门的id
    @RequestMapping("/info")
    public BaseResponse info(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object>resMap= Maps.newHashMap();
        Long deptId=0L;
        try{
            //数据视野决定顶级部门id可能不为0

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        resMap.put("deptId",deptId);
        response.setData(resMap);
        return response;
    }


    @RequestMapping("/list")
    public List<SysDeptEntity>list(){
        return sysDeptService.queryAll(Maps.newHashMap());
    }
}
