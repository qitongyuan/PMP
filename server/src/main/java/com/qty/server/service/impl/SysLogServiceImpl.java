package com.qty.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qty.model.entity.SysLogEntity;
import com.qty.model.mapper.SysLogDao;
import com.qty.server.service.SysLogService;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity>implements SysLogService {

}
