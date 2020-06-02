package com.damian.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_comment")
public class comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
//    时间类需要一个注解
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public comment(){

    }
    @ManyToOne
    private blog blog;

    @OneToMany(mappedBy = "parentcomment")
//    comment 的回复之间的自关联 ，一个父评论对应多个子评论
    private List<comment> replycomment = new ArrayList<>();

    @ManyToOne
    private comment parentcomment;

    private  boolean  admin;


    @Override
    public String toString() {
        return "comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<comment> getReplycomment() {
        return replycomment;
    }

    public void setReplycomment(List<comment> replycomment) {
        this.replycomment = replycomment;
    }

    public comment getParentcomment() {
        return parentcomment;
    }

    public void setParentcomment(comment parentcomment) {
        this.parentcomment = parentcomment;
    }

    public com.damian.blog.po.blog getBlog() {
        return blog;
    }

    public void setBlog(com.damian.blog.po.blog blog) {
        this.blog = blog;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getContent() {
        return content;
    }

    public String getAvatar() {
        return avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
