package com.damian.blog.service;

import com.damian.blog.NotFoundException;
import com.damian.blog.dao.TagRepository;
import com.damian.blog.po.tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@Service
public class tagServiceimp implements  tagService {
    @Override
    public List<tag> List() {
        return tagRepository.findAll();
    }

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<tag> ListTag1(String ids) {
        return tagRepository.findAllById(convertTolist(ids));
    }
//    写一个转换函数 把String 型的id转换成list
    private  List<Long> convertTolist(String ids){
            List<Long> list  =new ArrayList<>();
            if(!"".equals(ids) && ids !=null){
                String[] idarray = ids.split(",");
                for(int i=0; i < idarray.length; i++){
                    ((ArrayList) list).add(new Long (idarray[i]));
                }
            }
            return list;
    }
    @Transactional
    @Override
    public tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }
    @Transactional
    @Override
    public tag getTagById(Long id) {
        return tagRepository.getOne(id);
    }
    @Transactional
    @Override
    public tag saveTag(tag t) {
        return tagRepository.save(t);
    }

    @Override
    public List<tag> ListTag() {
        return tagRepository.findAll();
    }
    @Transactional
    @Override
    public Page<tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public tag updateTag(Long id, tag t) {
        tag Tag =  tagRepository.getOne(id);
        if(Tag ==null){
            throw new NotFoundException("不存在该id");
        }
        //       BeanUtils.copyPropertise  将t 中的数据复制到Type 实现数据的update
        BeanUtils.copyProperties(t,Tag);
        return tagRepository.save(Tag);
    }
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
