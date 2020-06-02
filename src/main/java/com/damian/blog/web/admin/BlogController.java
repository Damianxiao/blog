package com.damian.blog.web.admin;

import com.damian.blog.po.blog;
import com.damian.blog.po.user;
import com.damian.blog.service.blogService;
import com.damian.blog.service.tagService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-inputs";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";



    @Autowired
    private blogService blogService;

    @Autowired
    private typeService typeService;

    @Autowired
    private tagService tagService;
    @GetMapping("/blogs")
//    分页处理 统一都是这一个格式
    public String blogs(@PageableDefault(size= 10 ,  sort= {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model,
                        BlogQuery blog
    ){
//        注入typeService后，使用方法ListType找到type中所有的对象
//        需要在typeService中自行添加方法
        model.addAttribute("tags",tagService.ListTag());
        model.addAttribute("types",typeService.ListType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

//    使用fragment实现局部的动态刷新
    @PostMapping("/blogs/search")
    //    分页处理 统一都是这一个格式
    public String search(@PageableDefault(size= 4 ,  sort= {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model, BlogQuery blog
    ){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
//        需要初始化type 和 tag
        model.addAttribute("types",typeService.ListType());
        model.addAttribute("tags",tagService.ListTag());
        model.addAttribute("blog",new blog());
        return  INPUT;
    }

    private void  setTypeAndTag(Model model){
        model.addAttribute("types",typeService.ListType());
        model.addAttribute("tags",tagService.ListTag());
    }
//编辑 功能实现
    @GetMapping("/blogs/{id}/input")
    public String editinput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blogService.getBlog(id));
        return  INPUT;
    }

    @PostMapping("/blogs")
    public String post(blog blog,HttpSession session, RedirectAttributes attributes){
//        从session中取到user
//        在提交时还需要选择分类和标签，故在这里还需要初始化type 和 tags
         blog.setUser((user)session.getAttribute("user"));
//        blog.getType().getId()) 这里是取到type 中的id 所以要先gettype 然后再getid
         blog.setType(typeService.getType((blog.getType()).getId()));
         blog.setTags(tagService.ListTag1(blog.getTagIds()));
        blog b = blogService.saveBlog(blog);
         if(b == null){
             attributes.addFlashAttribute("message","提交失败");
         }
         else{
             attributes.addFlashAttribute("message","提交成功");
         }
        return REDIRECT_LIST;
    }
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id , RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }
}
