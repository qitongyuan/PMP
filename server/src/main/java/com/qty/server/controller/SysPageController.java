package com.qty.server.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SysPageController {

    /**
     * 跳转到对应模块页面中
     * @param module
     * @param page
     * @return
     */
    @RequestMapping("modules/{module}/{page}.html")
    public String page(@PathVariable String module,@PathVariable String page){
        return "modules/"+module+"/"+page;
    }

    @RequestMapping("login.html")
    public String login(){
        if (SecurityUtils.getSubject().isAuthenticated()){
            return "redirect:index.html";
        }
        return "login";
    }

    @RequestMapping(value = {"index.html","/"})
    public String index(){
        return "index";
    }

    @RequestMapping("main.html")
    public String main(){
        return "main";
    }

    @RequestMapping("404.html")
    public String notFound(){
        return "404";
    }

}
