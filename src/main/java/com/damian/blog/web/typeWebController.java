package com.damian.blog.web;


import com.damian.blog.service.blogService;
import com.damian.blog.service.typeService;
import com.damian.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class typeWebController {

    @Autowired
    private typeService typeService;

@Autowired
private blogService blogService;

    @GetMapping("/types/{id}")
    public String  types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @PathVariable  Long id, Model model){
        BlogQuery blogQuery = new BlogQuery();
//        这里传给的是type的id，每一个文章blog都对应一个id，若id一样则这些blog属于同一个分类
        blogQuery.setTypeId(id);
            model.addAttribute("type",typeService.getType(id));
            model.addAttribute("types", typeService.ListType());
//            blogservice里有一个分页查询
            model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        return "types";
    }
}

