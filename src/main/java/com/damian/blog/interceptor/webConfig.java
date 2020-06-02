package com.damian.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//新建一个配置类  表明拦截的是什么
//注解向spring boot 表明这是一个配置类
@Configuration
public class webConfig extends WebMvcConfigurerAdapter {

//    addInterceptor 添加新的拦截方法
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( new Logininterceptor())
                .addPathPatterns("/admin/**")
//                exclude 排除一个路径， 因为Logininterceptor 在检测admin 本身时会陷入循环
//                所以需要 排除 admin 本身的路径
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
