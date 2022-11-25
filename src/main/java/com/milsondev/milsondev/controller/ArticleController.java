package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.Comment;
import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.service.ArticleService;
import com.milsondev.milsondev.service.CommentService;
import com.milsondev.milsondev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping
public class ArticleController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    private boolean commentHasError = false;
    private Comment userComment = new Comment();

    private Errors errors2;

    //private List<String> defaulTagsList = Arrays.asList("custom login", "securty ", "spring boot", "spring security", "template", "thymeleaf");
    //private List<String> emptyTagList = new ArrayList<>();

    String defaulTagsList = "custom login; securty; spring boot; spring security; template; thymeleaf";


    @RequestMapping(value = "/page/article/{fileName}", method = RequestMethod.GET)
    public String openArticle2(@PathVariable String fileName) {
        return "redirect:/article/"+fileName;
    }


    @RequestMapping(value = "/article/{fileName}", method = RequestMethod.GET)
    public String openArticle(@PathVariable String fileName, Comment comment, Model model) {
        Article article = articleService.getArticleByFileName(fileName).get();
        comment = userComment;
        article.setViews(article.getViews() + 1);
        articleService.articleUpdate(article);
        model.addAttribute("article", article);

        List<Comment> comments = commentService.getCommentByArticle_Id(article.getId());
        model.addAttribute("comments", comments);

        if (commentHasError){
            if (errors2.hasFieldErrors("review")){
                model.addAttribute("errorCommentTextArea", "Write your comment");
            }else {
                model.addAttribute("errorCommentAuthor", "Enter your name");
            }
            commentHasError = false;
        } else {
            comment = new Comment();
        }

        model.addAttribute("comment", comment);

        return  article.getPath()+ fileName+".html";

    }

    @RequestMapping(value = "/edit/{fileName}", method = RequestMethod.GET)
    public ModelAndView editArticle(@PathVariable String fileName) {
        Article article = articleService.getArticleByFileName(fileName).get();
        ModelAndView mv = new ModelAndView("edit-article");
        User user = userService.getUser();
        mv.addObject("article", article);
        mv.addObject("userName", user.getUserName());
        return mv;
    }

    @RequestMapping(value = "/like/{fileName}", method = RequestMethod.GET)
    public String addLike(@PathVariable String fileName) {
        Article article = articleService.getArticleByFileName(fileName).get();
        article.setLikes(article.getLikes() + 1);
        articleService.articleUpdate(article);
        return "redirect:/article/"+fileName;
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
    public String newArticle(Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("defaulTagsList", defaulTagsList);
        model.addAttribute("article", new Article());
        return "new-article";
    }

    //Java Spring Boot Validation Thymeleaf
    @RequestMapping(value = "/add-new-article", method = RequestMethod.POST)
    public String createNewArticle(@Valid @ModelAttribute("article") Article article,
                                   Errors errors, RedirectAttributes attributes) throws IOException {
        if (errors.hasErrors()){
            attributes.addFlashAttribute("mensagem_erro", "Please fill out all necessary fields correctly!");
            return "redirect:/new-article";
        }

        User user = userService.getUser();
        article.setAuthor(user.getUserName());
        articleService.saveArticle(article);
        attributes.addFlashAttribute("mensagem", "New article was been successfully saved!");
        return "redirect:/new-article";
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

    @RequestMapping(value = "/post-comment", method = RequestMethod.POST)
    public String postComment (@Valid @ModelAttribute("comment") Comment comment,
                             Errors errors, Model model) throws IOException {
        if (errors.hasErrors()){
            commentHasError = true;
            errors2 = errors;
        } else {
            commentService.saveComment(comment);
        }

        userComment = comment;

        String fileName = articleService.getArticleFileNameById(comment.getArticle_id());
        Article article = articleService.getArticleByFileName(fileName).get();
        model.addAttribute("fileName", fileName);

        return "redirect:/article/"+fileName;

    }

}
