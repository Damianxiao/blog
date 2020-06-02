package com.damian.blog.web.admin;

import com.damian.blog.po.type;
import com.damian.blog.service.typeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private typeService typeService;

    @GetMapping("/types")
    public String  types(@PageableDefault(size =6 , sort = {"id"}, direction = Sort.Direction.DESC)
                                     Pageable pageable, Model model){

        model.addAttribute("page", typeService.listType(pageable));
        typeService.listType(pageable);
            return  "admin/types";
    }
    @GetMapping("/types/input")
    public String inputs (Model model){
        type t = new type();
        model.addAttribute("type",t);
        return "admin/types-inputs";
    }

    @GetMapping("/types/{id}/input")
//    必须加上一个@PathVariable注解 才能从路径{id}中取到id
    public String editinputs(@PathVariable  Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-inputs";
    }

    @PostMapping("/types")
//    @Valid用于校验结果  BandingResult 接收
        public String Post (@Valid type type, BindingResult result, RedirectAttributes attributes){
//  检测是否出现重复,从数据库中查询是否有这个name 若有，则一定出现了重复
        type t1 =typeService.getTypeByname(type.getName());
        if(t1 != null){
//            自定义错误
                result.rejectValue("name","nameErrors","不能重复添加分类");
        }
        if(result.hasErrors()){
            return "admin/types-inputs";
        }
        //        调用service方法保存
        type t = typeService.saveType(type);
                if(t==null){
//                    给一个保存失败的提示
                    attributes.addFlashAttribute("message","操作失败");
                }
                else{
//                      保存成功的提示
                    attributes.addFlashAttribute("message","操作成功");
                }
//                提交完成后还是选择返回分页的页面
            return "redirect:/admin/types";
        }

    @PostMapping("/types/{id}")
//    @Valid用于校验结果  BandingResult 接收
    public String editPost (@Valid type type, BindingResult result,
                           @PathVariable Long id,
                            RedirectAttributes attributes){
//  检测是否出现重复,从数据库中查询是否有这个name 若有，则一定出现了重复
        type t1 =typeService.getTypeByname(type.getName());
        if(t1 != null){
            result.rejectValue("name","nameErrors","不能重复添加分类");
        }
        if(result.hasErrors()){
            return "admin/types-inputs";
        }
        //        调用serviceupdate方法
        type t = typeService.updateType(id,type);
        if(t==null){
//                    给一个保存失败的提示
            attributes.addFlashAttribute("message","更新失败");
        }
        else{
//                      保存成功的提示
            attributes.addFlashAttribute("message","更新成功");
        }
//                提交完成后还是选择返回分页的页面
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable  Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功!");
        return  "redirect:/admin/types";
    }
}
