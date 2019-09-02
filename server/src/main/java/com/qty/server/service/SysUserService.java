package com.qty.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qty.model.entity.SysUserEntity;

public interface SysUserService extends IService<SysUserEntity> {

    //修改密码
    boolean updatePassword(Long userId,String oldPassword,String newPassword);
}
