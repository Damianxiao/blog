package com.damian.blog.service;

import com.damian.blog.po.tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface tagService {

    tag getTagByName(String name);

    tag getTagById(Long id);

    tag saveTag(tag t);

    List<tag> ListTag();

    List<tag> List();

    Page<tag> listTag (Pageable pageable);

    List<tag> ListTag1(String ids);

    tag updateTag(Long id,tag t);

    void  deleteTag(Long id);



}
