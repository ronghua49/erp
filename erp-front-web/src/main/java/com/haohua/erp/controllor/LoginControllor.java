package com.haohua.erp.controllor;    /*
 * @author  Administrator
 * @date 2018/7/26
 */

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller

public class LoginControllor {
    @GetMapping("/home")
    public String home(){
        return "home";
    }
    @GetMapping("/")
    public String employeeLogin(Model model){

        Subject subject = SecurityUtils.getSubject();
        //如果对象是被记住状态 则直接登录
        if (subject.isRemembered()){
            return  "home";
        }
        //如果是已经认证状态 则退出登录状态 到登录页面
        if(subject.isAuthenticated()){
            subject.logout();
        }
        //否则跳转登录页面
        return "index";
    }
    @PostMapping("/")
    public String employeeLogin(HttpServletRequest request ,
                                String userTel,
                                String password,
                                String remember,
                                RedirectAttributes redirectAttributes){
            String loginIp = request.getRemoteAddr();

             Subject subject = SecurityUtils.getSubject();
             UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userTel,DigestUtils.md5Hex(password),remember!=null,loginIp);
                try{
                        subject.login(usernamePasswordToken);
                    //判断是否具具有汽车接待角色
                    if(subject.hasRole("car:accept")){
                        //判断跳转路径
                        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                        String url = "/home";
                        //判断在登录前是否 从其他请求过来
                        if(savedRequest!=null){
                            url=savedRequest.getRequestUrl();
                        }
                        return  "redirect:"+url;
                    }else{
                        redirectAttributes.addFlashAttribute("message","您的权限不足");
                    }
                }catch (UnknownAccountException |IncorrectCredentialsException e){
                    redirectAttributes.addFlashAttribute("message","用户名或者密码错误");
                }catch (LockedAccountException e){
                    redirectAttributes.addFlashAttribute("message",e.getMessage());
                }catch(AuthenticationException a){
                    redirectAttributes.addFlashAttribute("message","登录失败！");
                }
                return "redirect:/";
    }
    @GetMapping("/401")
    public String unauthorizedUrl(){
        return "error/401";
    }

}


