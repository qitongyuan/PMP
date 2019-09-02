package com.qty.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qty.model.entity.SysUserEntity;
import com.qty.model.mapper.SysUserDao;
import com.qty.server.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
        //借助Mybatis-plus实现
        SysUserEntity entity=new SysUserEntity();
        entity.setPassword(newPassword);
        //调用dao更新
        Boolean res= this.update(entity,new QueryWrapper<SysUserEntity>().eq("user_id",userId)
        .eq("password",oldPassword));
        return res;
    }
}
