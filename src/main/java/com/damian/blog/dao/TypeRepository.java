package com.damian.blog.dao;

import com.damian.blog.po.type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<type,Long> {
    type findByName(String name);


//    按照每个分类所属的博客的个数 自定义语句
    @Query("select t from  type t")
    List<type> findTop(Pageable pageable);

}
