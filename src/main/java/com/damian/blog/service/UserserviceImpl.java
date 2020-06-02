package com.damian.blog.service;

import com.damian.blog.dao.UserRepository;
import com.damian.blog.po.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserserviceImpl implements userService {

//   建立一个新类实现接口
//    注入 接口 Repository
     @Autowired
    private UserRepository userRepository;

    @Override
//    检查用户名方法
    public user checkuser (String username, String password) {
        user user =  userRepository.findByUsernameAndPassword(username, password);
        return user;
    }
}
