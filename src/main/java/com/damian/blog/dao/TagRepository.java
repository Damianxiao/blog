package com.damian.blog.dao;

import com.damian.blog.po.tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<tag,Long> {
        tag findByName(String name);

}
