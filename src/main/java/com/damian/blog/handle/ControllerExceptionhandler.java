package com.damian.blog.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


//拦截exception
@ControllerAdvice
public class ControllerExceptionhandler {
//    使用logger 来记录日志 使用org.slf4j 包中的logger
//    getlogger this.getClass拿到对象

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//ModelAndView 用于控制页面，从后台向前端输出信息 通过request接收错误的路径

//  Exception.class表明任何异常都能被检测到
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
//        logger.error方法 ：记录异常信息 Request URL : {},  Exception :  {}  request 请求 错误的网站和信息
//        把URL 拿到传递到{}的位置。
            logger.error("Request URL : {},  Exception :  {}",  request.getRequestURL(),e);
//      在此处做一个注解，让handler有选择的拦截错误 传递一个状态的exception才能被识别,
//      如果存在则将拦截，不存在则将错误抛出，让spring-boot  自行处理
        if(AnnotationUtils.findAnnotation(e.getClass() , ResponseStatus.class)!= null){
            throw e;
        }

//            new一个对象
        ModelAndView mv =  new ModelAndView();
//        addobject 将之前获取的URL和错误信息添加到ModelAnView中
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
            return mv;
    }
}
