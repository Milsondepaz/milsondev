package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.Comment;
import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.service.ArticleService;
import com.milsondev.milsondev.service.CommentService;
import com.milsondev.milsondev.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping
public class ArticleController {
    private final UserServiceImpl userService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private boolean commentHasError = false;
    private Comment userComment = new Comment();
    private Errors myError;

    @Autowired
    public ArticleController (final UserServiceImpl userService, final ArticleService articleService, final CommentService commentService) {
        this.userService = userService;
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @RequestMapping(value = "/page/article/{fileName}", method = RequestMethod.GET)
    public String openArticlePage(@PathVariable String fileName) {
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
            if (myError.hasFieldErrors("review")){
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

    @RequestMapping(value = "/like/{fileName}", method = RequestMethod.GET)
    public String likeArticle(@PathVariable String fileName) {
        Article article = articleService.getArticleByFileName(fileName).get();
        article.setLikes(article.getLikes() + 1);
        articleService.articleUpdate(article);
        return "redirect:/article/"+fileName;
    }

    @RequestMapping(value = "/post-comment", method = RequestMethod.POST)
    public String postComment (@Valid @ModelAttribute("comment") Comment comment,
                             Errors errors, Model model) throws IOException {
        if (errors.hasErrors()){
            commentHasError = true;
            myError = errors;
        } else {
            commentService.saveComment(comment);
        }

        userComment = comment;

        String fileName = articleService.getArticleFileNameById(comment.getArticle_id());
        model.addAttribute("fileName", fileName);

        return "redirect:/article/"+fileName;

    }

    @RequestMapping(value = "/profile/{author}", method = RequestMethod.GET)
    public String profile(@PathVariable String author, Model model) {
        User user = userService.getUserbyName(author);
        model.addAttribute("user", user);
        return "profile";
    }

}
