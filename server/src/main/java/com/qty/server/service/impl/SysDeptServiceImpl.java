package com.qty.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qty.model.entity.SysDeptEntity;
import com.qty.model.mapper.SysDeptDao;
import com.qty.server.service.SysDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

    @Resource
    private SysDeptDao sysDeptDao;

    private static final Logger log= LoggerFactory.getLogger(SysDeptServiceImpl.class);

    @Override
    public List<SysDeptEntity> queryAll(Map<String, Object> map) {
        return sysDeptDao.queryList();
    }
}
