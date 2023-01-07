package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.Comment;
import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.service.ArticleService;
import com.milsondev.milsondev.service.CommentService;
import com.milsondev.milsondev.service.ImageService;
import com.milsondev.milsondev.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class ArticleController {
    private final UserServiceImpl userService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final ImageService imageService;

    private boolean commentHasError = false;
    private Comment userComment = new Comment();
    private Errors myError;

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    private Long idImage;
    private Long idAuthor;
    private Long idArticle;

    @Autowired
    public ArticleController (final UserServiceImpl userService, final ArticleService articleService,
                              final CommentService commentService, final ImageService imageService) {
        this.userService = userService;
        this.articleService = articleService;
        this.commentService = commentService;
        this.imageService = imageService;
    }

    @RequestMapping(value = "/page/article/{fileName}", method = RequestMethod.GET)
    public String openArticlePage(@PathVariable String fileName) {
        return "redirect:/article/"+fileName;
    }

    @RequestMapping(value = "/article/{fileName}", method = RequestMethod.GET)
    public String openArticle(@PathVariable String fileName, Comment comment, Model model) throws IOException {
        Article article = articleService.getArticleByFileName(fileName);

        articleService.articleIncrementView(article);

        model.addAttribute("article", article);
        model.addAttribute("user", userService.getUserbyId(article.getIdAuthor()));
        model.addAttribute("comments", commentService.getCommentList(article.getId()));

        return  article.getPath()+ fileName+".html";
    }



    @PostMapping(value = "/like")
    public ResponseEntity<Integer> subscribe(Long idArticle) {
        return new ResponseEntity<Integer>(articleService.incrementLikes(idArticle), HttpStatus.OK);
    }

    @PostMapping(value = "/post-comment")
    public ResponseEntity<?> postComment (@Valid @ModelAttribute("comment") Comment comment, BindingResult result) throws IOException {
        if (result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error: result.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.unprocessableEntity().body(errors);
        }
        commentService.saveComment(comment);
        return new ResponseEntity<List<Comment>>(commentService.getCommentList(comment.getArticle_id()), HttpStatus.OK);

    }

    @RequestMapping(value = "/profile/{author}", method = RequestMethod.GET)
    public String profile(@PathVariable String author, Model model) {
        User user = userService.getUserbyName(author);
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping(value = "/comments/ajax", method = RequestMethod.GET)
    public String reloadComments(Long idArticle, ModelMap model) {
        model.addAttribute("comments", commentService.getListWithLastComent(idArticle));
        return  "list-comments";
    }


}
