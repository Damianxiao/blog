package com.damian.blog.service;

import com.damian.blog.po.user;

public interface userService {
//    定义一个接口拦截非官方用户，以达到登录的目的
    user checkuser (String username , String password);

}
