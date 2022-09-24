package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.service.ArticleService;
import com.milsondev.milsondev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping
public class ArticleController {

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/article/{fileName}", method = RequestMethod.GET)
    public ModelAndView openArticle(@PathVariable String fileName) {
        Article article = articleService.getArticleByFileName(fileName).get();
        ModelAndView mv = new ModelAndView( article.getPath()+ fileName);
        article.setViews(article.getViews() + 1);
        articleService.articleUpdateNumbersOfViews(article);
        mv.addObject("article", article);
        mv.addObject("listTag", Arrays.asList("custom login", "securty ", "spring boot", "spring security", "template", "thymeleaf"));
        return mv;
    }

    @RequestMapping(value = "/edit/{fileName}", method = RequestMethod.GET)
    public ModelAndView editArticle(@PathVariable String fileName) {
        Article article = articleService.getArticleByFileName(fileName).get();
        ModelAndView mv = new ModelAndView("edit");
        User user = userService.getUser().get();
        mv.addObject("article", article);
        mv.addObject("userName", user.getUserName());
        return mv;
    }

    @RequestMapping(value = "/enable_disable/{id}", method = RequestMethod.GET)
    public String enableDisable(@PathVariable long id) {
        articleService.changeStateOfArticle(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String deleteArticle(@PathVariable long id) {
        articleService.deleteArticleById(id);
        return "redirect:/admin";
    }

    @GetMapping("/new-article")
    public ModelAndView newArticle( ) {
        User user = userService.getUser().get();
        ModelAndView mv = new ModelAndView("new-article");
        mv.addObject("user", user);
        mv.addObject("userName", user.getUserName());
        return mv;
    }

    @RequestMapping(value = "/add_new_article", method = RequestMethod.POST)
    public String createNewArticle(@Valid @ModelAttribute("article") Article article, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()){
            attributes.addFlashAttribute("mensagem_erro", "Please fill out all necessary fields correctly!");
            return "redirect:/new-article";
        }
        User user = userService.getUser().get();
        article.setAuthor(user.getUserName());
        articleService.saveArticle(article);
        attributes.addFlashAttribute("mensagem", "New article was been successfully saved!");
        return "redirect:/new-article";
    }

    @RequestMapping(value = "/update_article", method = RequestMethod.POST)
    public ModelAndView updateArticle(@Valid @ModelAttribute("article") Article article, Errors errors, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView("edit");
        User user = userService.getUser().get();
        mv.addObject("user", user);
        mv.addObject("userName", user.getUserName());

        if (errors.hasErrors()){
            mv.addObject("mensagem_erro", "Please fill out all necessary fields correctly!");
            return mv;
        }
        articleService.updateArticleInfo(article);
        mv.addObject("mensagem", "New article was been successfully updated!");

        return mv;
    }
}
