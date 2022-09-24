package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.service.ArticleService;
import com.milsondev.milsondev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.Optional;

@Controller
@RequestMapping
public class MilsonDevController {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @GetMapping("/")
    public ModelAndView index(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "4") int size, Model model ) {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("user", userService.getUser());
        mv.addObject("articleList", articleService.getPage(pageNumber, size));
        return mv;
    }
}
