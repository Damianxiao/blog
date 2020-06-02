package com.damian.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_tag")
public class tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;

//   tag对blog  多对多之间也有维护关系，默认选定tag为被维护关系
//    mappedby 根据对方的包含的关系来填写 在多对多的关系是LIst 的
//    tags 所以mappedBy中填tags

    @ManyToMany(mappedBy = "tags")
    private List<blog>  blogs = new ArrayList<>();

    public tag(){

    }


    public List<blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<blog> blogs) {
        this.blogs = blogs;
    }


    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
