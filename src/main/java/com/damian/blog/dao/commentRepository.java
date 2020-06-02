package com.damian.blog.dao;

import com.damian.blog.po.comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface commentRepository extends JpaRepository<comment,Long> {

    List<comment> findByBlogIdAndParentcommentIsNull(Long blogId, Sort sort);

}
