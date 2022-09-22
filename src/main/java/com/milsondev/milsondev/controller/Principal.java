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


import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class Principal {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ModelAndView index(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "4") int size, Model model ) {
        ModelAndView mv = new ModelAndView("index");
        User user = userService.getUser().get();
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
        User user = userService.getUser().get();
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
        User user = userService.updateUser(user_args);
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
        mv.addObject("articleList", articleService.getArticleList());
        User user = userService.getUser().get();
        mv.addObject("userName", user.getUserName());
        return mv;
    }

    @RequestMapping(value = "/article/{page}", method = RequestMethod.GET)
    public ModelAndView openArticle(@PathVariable String page) {
        Article article = articleService.getArticleByHtmlPage(page).get();

        ModelAndView mv = new ModelAndView( article.getPath()+ page);

        mv.addObject("article", article);
        mv.addObject("title", article.getTitle());
        mv.addObject("author", "milsondev");
        mv.addObject("createdUpdateOn", article.getFormatedDate());
        mv.addObject("viewed", article.getNumbersOfViews());
        mv.addObject("listTag", Arrays.asList("custom login", "securty ", "spring boot", "spring security", "template", "thymeleaf"));

        article.setNumbersOfViews(article.getNumbersOfViews() + 1);
        articleService.articleUpdateNumbersOfViews(article);

        return mv;
    }

    @RequestMapping(value = "/edit/{page}", method = RequestMethod.GET)
    public ModelAndView editArticle(@PathVariable String page) {
        Article article = articleService.getArticleByHtmlPage(page).get();
        ModelAndView mv = new ModelAndView("edit");
        mv.addObject("article", article);
        User user = userService.getUser().get();
        mv.addObject("userName", user.getUserName());
        return mv;
    }

    @RequestMapping(value = "/enable_disable/{id}", method = RequestMethod.GET)
    public String enableDisable(@PathVariable long id) {
        articleService.changeStateOfArticle(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String delteArticle(@PathVariable long id) {
        articleService.deleteArticleById(id);

        return "redirect:/admin";
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
