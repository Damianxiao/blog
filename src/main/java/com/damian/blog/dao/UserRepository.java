package com.damian.blog.dao;

import com.damian.blog.po.user;
import org.springframework.data.jpa.repository.JpaRepository;

//直接继承jpa ，可以直接使用jpa 中的方法 可以直接操作数据库
public interface UserRepository extends JpaRepository<user,Long> {
//这个方法根据用户名和密码查询 数据库
    user  findByUsernameAndPassword(String username , String password);
}
