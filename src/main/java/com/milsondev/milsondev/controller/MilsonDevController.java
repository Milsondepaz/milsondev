package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.Search;
import com.milsondev.milsondev.db.entities.Subscriber;
import com.milsondev.milsondev.service.ArticleService;
import com.milsondev.milsondev.service.SubscriberService;
import com.milsondev.milsondev.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.util.List;


// adicionar jQuery
// criar arquivo ao criar novo artigo
// subscribers - account validation
// securirity

// add photo profile -salvar no banco

// article page - share on Linkedin, facebook, whatsApp - done

// melhorar o layout da home


@Controller
@RequestMapping
public class MilsonDevController {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;

    @Autowired
    SubscriberService subscriberService;

    private static final Logger log = LoggerFactory.getLogger(MilsonDevController.class);

    /*
    @GetMapping("/")
    public String index(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "4") int size, Model model ) {
        model.addAttribute("user", userService.getUser());
        model.addAttribute("articleList", articleService.getPage(pageNumber, size));
        return "index";
    }
     */

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1,  model);
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("Search") Search Search, Model model) {

        int pageSize = 5;
        int pageNo = 1;

        Page<Article> page = articleService.findPaginatedAndFilter(pageNo, pageSize, Search.getSearchedWord());
        List<Article> articleList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("searchResult", "Your search has "+ page.getTotalElements() + " results...");

        model.addAttribute("articleList", articleList);
        return "index";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;

        Page<Article> page = articleService.findPaginated(pageNo, pageSize);
        List<Article> articleList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("articleList", articleList);
        return "index";
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public String subscribe(@Valid @ModelAttribute("subscriber") Subscriber subscriber, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()){
            attributes.addFlashAttribute("mensagem_erro", "Invalid email address!");
            return "redirect:/";
        }
        subscriberService.addSubscriber(subscriber);
        attributes.addFlashAttribute("mensagem", "You have successfully subscribed!");
        return "redirect:/";
    }
}
