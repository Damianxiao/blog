package com.damian.blog.web;

import com.damian.blog.service.blogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class archivesController {

    @Autowired
    private blogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap",blogService.archivesBlog());
        return "archives";
    }
}
