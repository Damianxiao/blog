package com.damian.blog.web;

import com.damian.blog.service.blogService;
import com.damian.blog.service.commentService;
import com.damian.blog.service.tagService;
import com.damian.blog.service.typeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Indexcontroller {

    @Autowired
    private typeService typeService;

    @Autowired
    private commentService commentService;


    @Autowired
    private blogService blogService;

    @Autowired
    private tagService tagService;

    @GetMapping("/")
//    分页的步骤也是一样的  这里是同样的分页1
    public String index(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("tag", tagService.ListTag());
        model.addAttribute("comments", commentService.ListComment());
        model.addAttribute("types", typeService.ListTypeTop(5));
        return "index";
    }

    @RequestMapping("/search")
    public String search(@PageableDefault(
            size = 4, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         RedirectAttributes attributes,
                         Model model, @RequestParam String query) {

//        由于blogRepository中的方法是使用了mysql中的查询语句，select b from t_blog where  title  like '%query%',所以我们还需要自己加上百分号
        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
//        将查询的字符串返回到search页面
        model.addAttribute("query", query);

        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PageableDefault(size = 4, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, RedirectAttributes attributes,
                       @PathVariable Long id, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }



}
