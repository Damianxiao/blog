package com.damian.blog.service;

import com.damian.blog.po.type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//在接口建立方法
public interface typeService {
    type getTypeByname(String name);
//  插入
    type saveType (type t);
//    根据id查询
    type getType (Long id);
//  查询返回page
    Page<type> listType (Pageable pageable);
//    修改
    List<type> ListType();

    List<type> ListTypeTop(Integer size);

    type updateType (Long id,type t);
//    删除
    void deleteType(Long id);
}
