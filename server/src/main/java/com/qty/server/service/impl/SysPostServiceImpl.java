package com.qty.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qty.model.entity.SysPostEntity;
import com.qty.model.mapper.SysPostDao;
import com.qty.server.service.SysPostService;
import com.qty.server.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostDao, SysPostEntity> implements SysPostService {

}
