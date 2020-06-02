package com.damian.blog.service;

import com.damian.blog.po.blog;
import com.damian.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface blogService {
    blog getBlog(Long id );

    blog getAndConvert(Long id);

    Page<blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<blog> listBlog(String query , Pageable pageable);

    Page<blog> listBlog(Pageable pageable);

    blog saveBlog(blog blog);

    Map<String , List<blog>> archivesBlog();

    blog updateBlog(Long id,blog blog);

    void deleteBlog(Long id);
}
