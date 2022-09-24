package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.service.ArticleService;
import com.milsondev.milsondev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @GetMapping("/sign-up")
    public String signup() {
        return "sign-up";
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute("user") User user, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()){
            attributes.addFlashAttribute("mensagem_erro", "Please fill out all necessary fields correctly!");
            return "redirect:/sign-up";
        }

        if (!user.getPassword().equals(user.getRepeatPassword())){
            attributes.addFlashAttribute("mensagem_erro", "The passowrd dont fit!");
            return "redirect:/sign-up";
        }

        userService.saveUser(user);
        attributes.addFlashAttribute("mensagem", "You have successfully registered, click to Login");

        return "redirect:/sign-up";
    }

    @GetMapping("/logout")
    public String logout( ) {
        return "redirect:/";
    }

    @GetMapping("/profile")
    public ModelAndView profile( ) {
        ModelAndView mv = new ModelAndView("profile");
        User user = userService.getUser();
        mv.addObject("user", user);
        mv.addObject("userName", user.getUserName());
        return mv;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/update_user", method = RequestMethod.POST)
    public String updateUser(@Valid @ModelAttribute("user") User user_args, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()){
            attributes.addFlashAttribute("mensagem_erro", "Please fill out all necessary fields correctly!");
            return "redirect:/profile";
        }
        User user = userService.updateUser(user_args);
        attributes.addFlashAttribute("user", user);
        attributes.addFlashAttribute("userName", user.getUserName());
        attributes.addFlashAttribute("mensagem", "Your information has been successfully updated!");
        return "redirect:/profile";
    }

    @GetMapping("/admin")
    public ModelAndView admin(Model model) {
        ModelAndView mv = new ModelAndView("admin");
        mv.addObject("articleList", articleService.getArticleList());
        User user = userService.getUser();
        mv.addObject("userName", user.getUserName());
        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("user") User user_arg) {
        ModelAndView mv;
        if(user_arg.getUserName().equals("milsona") && user_arg.getPassword().equals("12345")){
            mv = new ModelAndView("admin");
            mv.addObject("articleList", articleService.getArticleList());
            mv.addObject("userName", user_arg.getUserName());
            return mv;
        }

        mv = new ModelAndView("login");
        // add msm de login incorreto
        return mv;
    }

}
