package com.damian.blog.service;


import com.damian.blog.po.comment;

import java.util.List;

public interface commentService {

    List<comment> ListComment();

    List<comment> ListcommentByBlogId(Long blogId);

    comment saveComment(comment comment);
}
