package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.Article;
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
import org.thymeleaf.expression.Lists;


import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping
public class Principal {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    private User user = new User();
    private List <Article> articleList = new ArrayList<>() ;

    @PostConstruct
    public void init(){
        articleList = articleService.getArticleList();
        user = userService.getUser().get();
    }

    @GetMapping("/")
    public ModelAndView index(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "3") int size, Model model ) {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("user", user);
        mv.addObject("articleList", articleService.getPage(pageNumber, size));
        return mv;
    }

    @GetMapping("/logout")
    public String logout( ) {
        return "redirect:/";
    }

    @GetMapping("/newpost")
    public String newpost( ) {
        return "newpost";
    }

    @GetMapping("/profile")
    public ModelAndView profile( ) {
        ModelAndView mv = new ModelAndView("profile");
        mv.addObject("user", user);
        mv.addObject("userName", user.getUserName());
        return mv;
    }



    @RequestMapping(value = "/update_user", method = RequestMethod.POST)
    public String updateUser(@Valid @ModelAttribute("user") User user_args, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()){
            attributes.addFlashAttribute("mensagem_erro", "Please fill out all necessary fields correctly!");
            return "redirect:/profile";
        }
        user = userService.updateUser(user_args);
        attributes.addFlashAttribute("user", user);
        attributes.addFlashAttribute("userName", user.getUserName());
        attributes.addFlashAttribute("mensagem", "Your information has been successfully updated!");
        return "redirect:/profile";
    }


    //1- spring secuirity
    // profile
    // edite all informartion on profile
    // profile blog area
    // list post
    // create new post
    // edit and delete


    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public ModelAndView blog(@ModelAttribute("article") Article article, RedirectAttributes attributes) {
        Article article2 = articleService.getArticleByHtmlPage (article.getPage()).get();
        ModelAndView mv = new ModelAndView(article2.getPath()+article2.getPage());
        mv.addObject("article", article2);
        mv.addObject("title", article2.getTitle());
        mv.addObject("author", "milsondev");
        mv.addObject("createdUpdateOn", article.getFormatedDate());
        mv.addObject("viewed", article2.getNumbersOfViews());
        article2.setNumbersOfViews(article2.getNumbersOfViews() + 1);
        articleService.articleUpdateNumbersOfViews(article2);
        List<String> listTag = new ArrayList<>(Arrays.asList("custom login", "securty ", "spring boot", "spring security", "template", "thymeleaf"));
        mv.addObject("listTag", listTag);








        return mv;
    }

    @GetMapping("/admin")
    public ModelAndView admin(Model model) {
        ModelAndView mv = new ModelAndView("admin");
        mv.addObject("articleList", articleList);
        mv.addObject("userName", user.getUserName());
        return mv;
    }

    @GetMapping("/{id}")
    public void delteArticle(int id) {

        System.out.println("delete article "+ id);

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("user") User user_arg) {
        ModelAndView mv;
        if(user_arg.getUserName().equals("milsona") && user_arg.getPassword().equals("12345")){
            mv = new ModelAndView("admin");
            mv.addObject("articleList", articleList);
            mv.addObject("userName", user_arg.getUserName());
            return mv;
        }

        mv = new ModelAndView("login");
        // add msm de login incorreto
        return mv;
    }





}
