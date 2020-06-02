package com.damian.blog.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_type")
public class type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    public void setId(Long id) {
        this.id = id;
    }

    //    校验type对象
    @NotBlank(message="分类名称不能为空")
    private  String name;
//    type 对应多个blog 实体,使用list
//    一对多的关系
//    在many的一端做关系维护,One 一端是关系被维护端
//    mappedBy 代表被维护
    @OneToMany(mappedBy = "type")
    private List<blog> blogs = new ArrayList<>();

    public  type(){

    }

    @Override
    public String toString() {
        return "type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public List<blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<blog> blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
