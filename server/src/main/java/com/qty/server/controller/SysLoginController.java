package com.qty.server.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.qty.common.response.BaseResponse;
import com.qty.common.response.StatusCode;
import com.qty.server.annotation.LogAnnotation;
import com.qty.server.shiro.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;


@Controller
public class SysLoginController extends AbstractController{

    @Autowired
    private Producer producer;

    /**
     * 调用此接口生成验证码
     * @param response
     * @throws Exception
     */
    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response)throws Exception{
        response.setHeader("Cache-Control","no-store, no-cache");
        response.setContentType("image/jpg");
        //以上设置请求头,将其输出成图片

        //生成文字校验码
        String text=producer.createText();
        //生成图片校验码
        BufferedImage image=producer.createImage(text);
        //保存到shrio的session中
        Session session=SecurityUtils.getSubject().getSession();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY,text);
        ServletOutputStream out=response.getOutputStream();
        ImageIO.write(image,"jpg",out);
    }

    @RequestMapping(value = "/sys/login",method = RequestMethod.POST)
    @LogAnnotation("用户登录")
    @ResponseBody
    public BaseResponse login(String username,String password,String captcha){
        log.info("用户名：{} 密码：{} 验证码:{}",username,password,captcha);

        //首先验证验证码是否正确
        String kaptcha=ShiroUtil.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if (!kaptcha.equals(captcha)){
            return new BaseResponse(StatusCode.InvalidCode);
        }
        //校验账号密码
        try{
            Subject subject= SecurityUtils.getSubject();
            //如果没有认证登录的话
            if (!subject.isAuthenticated()){
                UsernamePasswordToken token=new UsernamePasswordToken(username,password);
                subject.login(token);
            }
        }catch (UnknownAccountException e){
            return new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }catch (IncorrectCredentialsException e){
            return new BaseResponse(StatusCode.AccountPasswordNotMatch);
        }catch (LockedAccountException e){
            return new BaseResponse(StatusCode.AccountHasBeenLocked);
        }catch (AuthenticationException e){
            return new BaseResponse(StatusCode.AccountValidateFail);
        }
        return new BaseResponse(StatusCode.Success);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(){
        //销毁当前shiro的用户session
        ShiroUtil.logout();
        return "redirect:login.html";
    }
}
