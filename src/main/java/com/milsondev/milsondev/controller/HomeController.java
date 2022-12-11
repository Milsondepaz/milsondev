package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.Search;
import com.milsondev.milsondev.db.entities.Subscriber;
import com.milsondev.milsondev.service.ArticleService;
import com.milsondev.milsondev.service.SubscriberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {
    private final ArticleService articleService;
    private final SubscriberService subscriberService;
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    /*
    Subscribers
    Subscribers- ver
    Export Subscriber via csv
    IP - visitors
    IP - visitors - ver
    Upload imagens for profile picture
    Upload imagens for article cover
     */

    @Autowired
    public HomeController(final ArticleService articleService, final SubscriberService subscriberService) {
        this.articleService = articleService;
        this.subscriberService = subscriberService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        LOGGER.info("Log - Home");
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
        LOGGER.info("Log - Search:" + Search.getSearchedWord());
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
        LOGGER.info("Log - Paginated");
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
        LOGGER.info("Log - Subscriber: " + subscriber.getEmail());
        return "redirect:/";
    }
}