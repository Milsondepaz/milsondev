package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.service.ArticleService;
import com.milsondev.milsondev.service.SubscriberService;
import com.milsondev.milsondev.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    private final UserServiceImpl userService;
    private final ArticleService articleService;
    private final SubscriberService subscriberService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    public AdminUserController(final ArticleService articleService, final UserServiceImpl userService, SubscriberService subscriberService){
        this.articleService = articleService;
        this.userService = userService;
        this.subscriberService = subscriberService;
    }

    @GetMapping("")
    public ModelAndView admin(Model model) {
        ModelAndView mv = new ModelAndView("admin");
        mv.addObject("articleList", articleService.getArticleList());
        mv.addObject("subscriberList", subscriberService.getSubscriberList());
        User user = userService.getUser();
        mv.addObject("userName", user.getUserName());
        return mv;
    }

    @GetMapping("/edit-user-profile")
    public String profile( Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        model.addAttribute("userName", user.getUserName());
        return "edit-user-profile";
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
    public String updateUser(@Valid @ModelAttribute("user") User user_args, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()){
            attributes.addFlashAttribute("mensagem_erro", "Please fill out all necessary fields correctly!");
            return "redirect:/admin/edit-user-profile";
        }
        User user = userService.updateUser(user_args);
        attributes.addFlashAttribute("user", user);
        attributes.addFlashAttribute("userName", user.getUserName());
        attributes.addFlashAttribute("mensagem", "Your information has been successfully updated!");
        return "redirect:/admin/edit-user-profile";
    }

}
