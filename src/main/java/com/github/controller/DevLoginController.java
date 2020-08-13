package com.github.controller;

import com.github.pojo.DevUser;
import com.github.service.DevUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/dev")//请求前缀
public class DevLoginController {

    @Resource
    private DevUserService devUserService;

    @RequestMapping("/login")
    public String devLogin(){
        return "devlogin";
    }

    /**
     * 注销
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public String logOut(HttpServletRequest request){
        //删除session
        request.getSession().removeAttribute("devUserSession");
        return "redirect:/dev/login";
    }
    /**
     * 登录
     * @param request
     * @param model
     * @param devCode
     * @param devPassword
     * @return
     */
    @RequestMapping("/dologin")
    public String doLogin(HttpServletRequest request,Model model, @RequestParam String devCode,@RequestParam String devPassword){
        /*1.判空处理*/
        if (devCode == "" || devPassword == ""){
            request.setAttribute("error","用户名或者密码不能为空!");
            return "devlogin";
        }else{

        }
        DevUser devUser = devUserService.doLogin(devCode,devPassword);
        if (devUser == null){
            request.setAttribute("error","用户名或者密码错误!");
            return "devlogin";
        }
        request.getSession().setAttribute("devUserSession",devUser);
        return "developer/main";
    }
}
