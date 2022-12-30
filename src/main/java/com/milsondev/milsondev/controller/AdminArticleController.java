package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminArticleController {
    private final UserServiceImpl userService;
    private final ArticleService articleService;
    private final ImageService imageService;
    private String defaulTagsList = "custom login; securty; spring boot; spring security; template; thymeleaf";

    @Autowired
    public AdminArticleController (final UserServiceImpl userService, final ArticleService articleService, ImageService imageService) {
        this.userService = userService;
        this.articleService = articleService;
        this.imageService = imageService;
    }

    @RequestMapping(value = "/article/{fileName}", method = RequestMethod.GET)
    public String openArticleFromAdmin(@PathVariable String fileName) {
        return "redirect:/article/"+fileName;
    }

    @RequestMapping(value = "/edit-article/{fileName}", method = RequestMethod.GET)
    public ModelAndView editArticle(@PathVariable String fileName) {
        Article article = articleService.getArticleByFileName(fileName).get();
        ModelAndView mv = new ModelAndView("edit-article");
        User user = userService.getUser();
        mv.addObject("article", article);
        mv.addObject("userName", user.getUserName());
        return mv;
    }


    @RequestMapping(value = "/enable-disable/{id}", method = RequestMethod.GET)
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
    public String newArticle(Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("defaulTagsList", defaulTagsList);
        model.addAttribute("article", new Article());
        return "new-article";
    }

    @RequestMapping(value = "/add-new-article", method = RequestMethod.POST)
    public String createNewArticle(@Valid @ModelAttribute("article") Article article,
                                   Errors errors, RedirectAttributes attributes) throws IOException {
        if (errors.hasErrors()){
            attributes.addFlashAttribute("mensagem_erro", "Please fill out all necessary fields correctly!");
            return "redirect:/admin/new-article";
        }

        // se esse linhar nao passar com sucesso lancar excessao e cancelar tudo // colocar dentro dum try
        Long idImage = imageService.uploadImage((article.getImageFile()));
        article.setIdImage(idImage);

        Long userId = userService.getAuthenticatedUserId();
        article.setIdAuthor(userId);

        articleService.saveArticle(article);

        attributes.addFlashAttribute("mensagem", "New article was been successfully saved!");

        return "redirect:/admin/new-article";
    }

    @RequestMapping(value = "/update_article", method = RequestMethod.POST)
    public ModelAndView updateArticle(@Valid @ModelAttribute("article") Article article, Errors errors, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView("edit-article");
        User user = userService.getUser();
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
