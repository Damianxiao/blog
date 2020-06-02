package com.damian.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//设置与数据库相关的注解
@Entity
@Table(name = "t_blog")
public class blog {
//    主键标记
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private boolean appreciation;
    private boolean shareStatement;
    private String proFile;

    @Override
    public String toString() {
        return "blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", proFile='" + proFile + '\'' +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                '}';
    }

    public String getProFile() {
        return proFile;
    }

    public void setProFile(String proFile) {
        this.proFile = proFile;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private Date createTime;
    private  Date updateTime;

//blog 和 type 多对一的关系，
// 构建他们实体之间的关系通过以下方法@manytoone
//在many的一端做关系维护,One 一端是关系被维护端
    @ManyToOne()
//    blog 是many
    private  type type;
//blog 和tag是多对多的关系 因此在两端都存在一个对方的LIst
    @ManyToMany(cascade = {CascadeType.PERSIST})
//    cascade 是级联新增关系 当新增了一个blog时同时增加 一个tag
    private List<tag> tags = new ArrayList<>();

    @ManyToOne
    private user user;

    @OneToMany(mappedBy = "blog")
    private List<comment> comments = new ArrayList<>();

//    这个注释代表它不属于数据库之中的数据
    @Transient
    private String tagIds;

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public blog (){

    }

    public List<comment> getComments() {
        return comments;
    }

    public void setComments(List<comment> comments) {
        this.comments = comments;
    }

    public com.damian.blog.po.user getUser() {
        return user;
    }

    public void setUser(com.damian.blog.po.user user) {
        this.user = user;
    }

    public List<tag> getTags() {
        return tags;
    }

    public void setTags(List<tag> tags) {
        this.tags = tags;
    }

    public type getType() {
        return type;
    }

    public void setType(com.damian.blog.po.type type) {
        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }


    public void setViews(Integer views) {
        this.views = views;
    }


    public Long getId() {
        return id;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public Integer getViews() {
        return views;
    }


    public boolean isRecommend() {
        return recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }
    private String tagsToIds(List<tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for(tag tag : tags){
                if(flag){
                    ids.append(",");
                }else{
                    flag= true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else{
            return tagIds;
        }
    }


}
