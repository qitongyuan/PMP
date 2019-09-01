package com.qty.server.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qty.model.entity.SysUserEntity;
import com.qty.model.mapper.SysUserDao;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserRealm extends AuthorizingRealm {

    @Resource
    private SysUserDao sysUserDao;

    private static final Logger log= LoggerFactory.getLogger(UserRealm.class);
    /**
     * 用户登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        String userName=token.getUsername();
        String password= String.valueOf(token.getPassword());
        log.info("用户名： {} 密码：{} ",userName,password);
        SysUserEntity entity= sysUserDao.selectOne(new QueryWrapper<SysUserEntity>().eq("username",userName));
        if(null==entity){
            throw new UnknownAccountException("账户不存在");
        }
        if (0==entity.getStatus()){
            throw new DisabledAccountException("账户已被禁用");
        }
        /*这里需要先加密才能对比数据库*/
        /*生产环境一般不这么用*/
/*        if (!entity.getPassword().equals(password)){
            throw new IncorrectCredentialsException("密码不对");
        }
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(entity,password,getName());*/

        //实际生产环境Realm这样匹配密码
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(entity,entity.getPassword(), ByteSource.Util.bytes(entity.getSalt()),getName());
        return info;
    }

    /**
     * 密码验证器，设置用于验证的逻辑
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(ShiroUtil.hashAlgorithmName);
        hashedCredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);
        super.setCredentialsMatcher(hashedCredentialsMatcher);
    }

    /**
     * 用户授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
