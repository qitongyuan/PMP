package com.qty.server.shiro;

import com.qty.model.entity.SysUserEntity;
import com.qty.server.exception.CommonKaptchaException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;

/**
 * shiro的工具类
 */
public class ShiroUtil {

    //用于加密的密码的算法
    public final static String hashAlgorithmName ="SHA-256";

    //循环的次数
    public final static int hashIterations =16;

    //传入密码和盐值返回加密后的密码
    public static String sha256(String password,String salt){
        return new SimpleHash(hashAlgorithmName,password,salt,hashIterations).toString();
    }


    /**
     * 拿到校验码
     * @param key
     * @return
     */
    public static String getKaptcha(String key){
        Session session=SecurityUtils.getSubject().getSession();
        Object object=session.getAttribute(key);
        if (null==object){
            throw new CommonKaptchaException("验证码失效");
        }
        String newCode=object.toString();
        session.removeAttribute(key);
        System.out.println("要对比的验证码为 "+newCode);
        return newCode;
    }

    /**
     *  shiro拿到登录的实体用户
     * @return
     */
    public static SysUserEntity getUserEntity(){
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 退出登录
     */
    public static void logout(){
        SecurityUtils.getSubject().logout();
    }
}
