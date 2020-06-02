package com.damian.blog.dao;

import com.damian.blog.po.blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

//额外还继承了一个JpaSpecificationExecutor 更方便的调用pageable
public interface BlogRepository extends JpaRepository<blog, Long>, JpaSpecificationExecutor<blog> {

    //需要在repository自定义方法，因为在jpa中没有对应需要的方法：
//    1和2代表第一个参数和第二个参数
    @Query("select b from blog b where b.title like ?1 or b.content like ?1")
    Page<blog> findByQuery(String query, Pageable pageable);


    @Transactional
    @Modifying
    @Query("update blog b set b.views = b.views+1 where b.id= ?1")
    int updateViews(Long id);

//查询方法中的数据要用以下格式 date_format是格式化时间的方法

    @Query("select  function('date_format'  ,  b.updateTime, '%Y') as year from blog b group by  " +
            " function('date_format'  ,  b.updateTime, '%Y') order by year desc ")
    List<String> findGroupYears();


    //?1代表的是下面函数传过来的参数，这里是string year
    @Query("select b from blog b where function('date_format', b.updateTime,'%Y') =  ?1 ")
    List<blog> findByYear(String year);
}