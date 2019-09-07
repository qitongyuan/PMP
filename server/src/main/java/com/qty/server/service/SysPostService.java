package com.qty.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qty.common.utils.PageUtil;
import com.qty.model.entity.SysPostEntity;

import java.util.Map;

public interface SysPostService extends IService<SysPostEntity> {

    PageUtil queryPage(Map<String,Object> params);

    void savePost(SysPostEntity entity);

    void updatePost(SysPostEntity entity);
}
