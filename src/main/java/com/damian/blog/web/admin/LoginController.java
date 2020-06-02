package com.damian.blog.web.admin;


import com.damian.blog.po.user;
import com.damian.blog.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private userService userService;
    @GetMapping
//    跳转到登录页面
    public String loginPage(){
         return "admin/login";
    }
//    登录提交用户名 使用post 方法提交
//    post 提交参数要加 @RequestParam 接收数据
    @PostMapping("/login")
    public String login(@RequestParam(name="username") String username,
                        @RequestParam(name="password")String password,
                        HttpSession session,
                        RedirectAttributes attributes
    ){
//        把username password 传进检测方法 checkuser中
        user user = userService.checkuser(username, password);
        if(user != null){
//            user.setPassword(null)
//            使用session 存取数据 使用setAttribute 存数据 使用  getAttribute取出数据
//            attributes 返回指定数据的集合
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }
        else{
//            若密码错误则直接返回到登录界面 这里使用attributes 返回给前端页面 一个message
            attributes.addFlashAttribute("message","用户名或密码错误");
            return  "redirect:/admin";
        }
    }
//登出操作  将session中的数据清除
    @GetMapping("/logout")
    public String logout (HttpSession session){
        session.removeAttribute("user");
        return  "redirect:/admin";

//        session 在同一个浏览器是共享的，除非重新启动项目或者不在当前浏览器 登录
    }


}
