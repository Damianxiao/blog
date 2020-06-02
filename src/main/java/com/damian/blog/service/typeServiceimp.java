package com.damian.blog.service;

import com.damian.blog.NotFoundException;
import com.damian.blog.dao.TypeRepository;
import com.damian.blog.po.type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class typeServiceimp implements typeService {

//    注入repository
    @Autowired
    private TypeRepository typeRepository;


    @Override
    public List<type> ListTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable =PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    //    有关增删改查的方法都放在transactional  注解里
    @Transactional
    @Override
    public type saveType(type t) {
//        直接调用jpa方法实现保存
        return typeRepository.save(t);
    }
    @Transactional
    @Override
    public type getType(Long id) {
        return typeRepository.getOne(id);
    }
    @Transactional
    @Override
    public Page<type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<type> ListType() {
        return typeRepository.findAll();
    }

    @Transactional
    @Override
    public type updateType(Long id, type t) {
        type Type =  typeRepository.getOne(id);
        if(Type ==null){
            throw new NotFoundException("不存在该id");
        }
//       BeanUtils.copyPropertise  将t 中的数据复制到Type 实现数据的update
        BeanUtils.copyProperties(t,Type);
        return typeRepository.save(Type);
    }
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public type getTypeByname (String name){
        return typeRepository.findByName(name);
    }
}

