package com.damian.blog.service;


import com.damian.blog.NotFoundException;
import com.damian.blog.dao.BlogRepository;
import com.damian.blog.po.blog;
import com.damian.blog.po.type;
import com.damian.blog.util.markdownUtil;
import com.damian.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//必须要加上Service
@Service
public class blogServiceimp implements blogService {


    @Override
    public Map<String, List<blog>> archivesBlog() {
        List<String > years =  blogRepository.findGroupYears();
        Map<String , List<blog>> map =  new HashMap<>();
        for(String year : years){
                map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Transactional
    @Override
    public blog getAndConvert(Long id)  {
        blog blog = blogRepository.getOne(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在");
        }
//        不改变数据库中存储的类型
      blog b = new blog();
        BeanUtils.copyProperties(blog,b);
            String content = blog.getContent();
//        调用util方法 将markdown转换为html
//        转换之后再set
        b.setContent(markdownUtil.markdownToHtmlExtensions(content));;
//        最后返回已经修改过后的blog

        blogRepository.updateViews(id);
        return b ;
    }

    @Override
    public Page<blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Page<blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Page<blog> listBlog(Pageable pageable, BlogQuery blog) {
//        引用JpaSpecificationExecutor中的方法finAll
        return blogRepository.findAll(new Specification<blog>() {
            @Override
//           toPredicate 用于帮助 动态查询
            public Predicate toPredicate(Root<blog> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(! "" .equals(blog.getTitle()) && blog.getTitle()  != null){
//                    构造了一个like 查询的条件
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%"+ blog.getTitle()+"%"));
                }
//                查询type ： 利用blog类中有一个type 对象
                if(blog.getTypeId()!= null ){
                    predicates.add(criteriaBuilder.equal((root.<type>get("type").get("id")),
                            blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable) ;
    }

    @Override
    public blog saveBlog(blog blog) {
//        当blog id 为空是  则说明是新增的，不等于空则是修改
        if(blog.getId() == null){
            blog.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
            blog.setUpdateTime(new java.sql.Date(System.currentTimeMillis()));
//       setViews为浏览次数
            blog.setViews(0);
        }
        else{
            blog.setUpdateTime(new java.sql.Date(System.currentTimeMillis()));
//       setViews为浏览次数
            blog.setViews(0);
        }
        return blogRepository.save(blog);
    }

    @Override
    public blog updateBlog(Long id, blog blog) {
        blog b = blogRepository.getOne(id);
        if(b ==null){
            throw  new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(b,blog);
        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(Long id) {
            blogRepository.deleteById(id);
    }
}
