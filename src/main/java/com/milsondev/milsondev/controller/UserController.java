package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.service.ArticleService;
import com.milsondev.milsondev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
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
        attributes.addFlashAttribute("mensagem", "You have successfully registered, click to ");

        return "redirect:/sign-up";
    }

    @GetMapping("/logout")
    public String logout( ) {
        return "redirect:/";
    }

    //edit-profile/{userName}
    @GetMapping("/edit-profile")
    public String profile( Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        model.addAttribute("userName", user.getUserName());
        return "edit-profile";
    }

    //edit-profile/{userName}
    @RequestMapping(value = "/profile/{author}", method = RequestMethod.GET)
    public ModelAndView profile(@PathVariable String author, Model model) {
        User user = userService.getUserbyName(author);
        ModelAndView mv = new ModelAndView("profile");
        mv.addObject("user", user);
        return mv;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
    public String updateUser(@Valid @ModelAttribute("user") User user_args, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()){
            attributes.addFlashAttribute("mensagem_erro", "Please fill out all necessary fields correctly!");
            return "redirect:/edit-profile";
        }
        User user = userService.updateUser(user_args);
        attributes.addFlashAttribute("user", user);
        attributes.addFlashAttribute("userName", user.getUserName());
        attributes.addFlashAttribute("mensagem", "Your information has been successfully updated!");
        return "redirect:/edit-profile";
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
        User user = userService.getUser();
        if(user_arg.getUserName().equals(user.getUserName()) && user_arg.getPassword().equals(user.getPassword())){
            mv = new ModelAndView("admin");
            mv.addObject("articleList", articleService.getArticleList());
            mv.addObject("userName", user_arg.getUserName());
            return mv;
        }

        mv = new ModelAndView("login");
        mv.addObject("mensagem_erro", "Invalid credentials!");
        return mv;
    }

}
