package com.damian.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//使用interceptor 拦截非登陆用户，未登录用户不能访问
//admin 后跟的链接
public class Logininterceptor  extends HandlerInterceptorAdapter {
    @Override
//    prehandler预处理 接收之前先处理
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
//        从session 里面 提取user数据 如果user  中为空
//        则会重新定向到 /admin 登录页面 无法进入
        if(request.getSession().getAttribute("user")==null){
            response.sendRedirect("/admin");
            return  false;
        }

        return true;
    }
}
