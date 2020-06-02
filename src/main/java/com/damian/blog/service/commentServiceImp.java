package com.damian.blog.service;

import com.damian.blog.dao.commentRepository;
import com.damian.blog.po.comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class commentServiceImp implements commentService {

    @Autowired
    private commentRepository commentRepository;


    @Override
    public List<comment> ListComment() {
        return commentRepository.findAll();
    }

    @Override
    public List<comment> ListcommentByBlogId(Long blogId) {
//        这里由于springboot的 版本更新需要更换sort方法的引用方式
        Sort sort = Sort.by("createTime");
        List<comment> comments = commentRepository.findByBlogIdAndParentcommentIsNull(blogId, sort);
        return eachComment(comments);
    }


    @Transactional
    @Override
    public comment saveComment(comment comment) {
        Long parentCommentId = comment.getParentcomment().getId();
//        parentcommentId不等于null证明这个评论是子评论
        if (parentCommentId != -1) {
//            此写法是spring 高版本的新特性
            comment.setParentcomment(commentRepository.findById(parentCommentId).orElse(null));
        } else {
            comment.setParentcomment(null);
        }
//        更新时间
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    //    循环父级评论

    /**
     * 循环每个顶级的评论节点
     *
     * @param comments
     * @return
     */
    private List<comment> eachComment(List<comment> comments) {
        List<comment> commentsView = new ArrayList<>();
        for (comment comment : comments) {
            comment c = new comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<comment> comments) {

        for (comment comment : comments) {
            List<comment> replys1 = comment.getReplycomment();
            for (comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplycomment(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     *
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplycomment().size() > 0) {
            List<comment> replys = comment.getReplycomment();
            for (comment reply : replys) {
                if (reply.getReplycomment().size() > 0) {
                    recursively(reply);
                }
                else{
                    tempReplys.add(reply);
                }
            }
        }
    }
}




