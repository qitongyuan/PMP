package com.qty.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qty.model.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

public interface SysDeptService extends IService<SysDeptEntity> {

    List<SysDeptEntity>queryAll(Map<String,Object>map);
}
