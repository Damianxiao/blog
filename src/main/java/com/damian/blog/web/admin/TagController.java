package com.damian.blog.web.admin;

import com.damian.blog.po.tag;
import com.damian.blog.service.tagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController  {
//    注入jpa方法

    @Autowired
    private tagService tagService;

    @GetMapping("/tags")
        public String tags(@PageableDefault(size =10 , sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
            model.addAttribute("page",tagService.listTag(pageable));
            tagService.listTag(pageable);
            return  "admin/tags";
    }

    @GetMapping("/tags/input")
        public String inputs (Model model){
    //        addattribute往前端传数据
            tag t = new tag();
            model.addAttribute("tag",t);
            return "admin/tags-inputs";
    }

    @GetMapping("/tags/{id}/input")
        public String editinputs(@PathVariable Long id ,Model model ){
            model.addAttribute("tag",tagService.getTagById(id));
            return "admin/tags-inputs";
    }
    @PostMapping("/tags")
        public String Post(@Valid tag tag, BindingResult result, RedirectAttributes attributes){
        tag t1 =tagService.getTagByName(tag.getName());
            if(t1!= null){
                result.rejectValue("name","nameError","不能重复添加！");
            }
            if(result.hasErrors()){
                return "admin/tags-inputs";
            }
            /*如果提交正确，则保存*/
            tag t  = tagService.saveTag(tag);
                if(t ==null){
                    attributes.addFlashAttribute("message","操作失败");
                }
                else{
                    attributes.addFlashAttribute("message","操作成功");
                }
            return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
//    @Valid用于校验结果  BandingResult 接收
    public String editPost (@Valid tag tag, BindingResult result,
                            @PathVariable Long id,
                            RedirectAttributes attributes){
//  检测是否出现重复,从数据库中查询是否有这个name 若有，则一定出现了重复
        tag t1 =tagService.getTagByName(tag.getName());
        if(t1 != null){
            result.rejectValue("name","nameErrors","不能重复添加分类");
        }
        if(result.hasErrors()){
            return "admin/tags-inputs";
        }
        //        调用serviceupdate方法
        tag t = tagService.updateTag(id,tag);
        if(t==null){
//                    给一个保存失败的提示
            attributes.addFlashAttribute("message","更新失败");
        }
        else{
//                      保存成功的提示
            attributes.addFlashAttribute("message","更新成功");
        }
//                提交完成后还是选择返回分页的页面 一定要使用redirect 重新定向
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id , RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/tags";
    }





}
