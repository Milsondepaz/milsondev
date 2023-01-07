package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.Search;
import com.milsondev.milsondev.db.entities.Subscriber;
import com.milsondev.milsondev.service.ArticleService;
import com.milsondev.milsondev.service.ImageService;
import com.milsondev.milsondev.service.SubscriberService;
import com.milsondev.milsondev.service.VisitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {
    private final ArticleService articleService;
    private final SubscriberService subscriberService;
    private final VisitorService visitorService;
    private final ImageService imageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    /*
    Upload imagens for profile picture
    Upload imagens for article cover

    colocar a imagem do cover do artico na tabela artigo
    colocar a imagem do author na tabela do author
    fazer isso via ajax
    animar botao like via ajax
    colocar os botos de partilhar e copia link no fim do artigo
    diminuir espaco entre mini-profile do author e texto
    refatorar
    colocar loggs
    colocar excessoes
    docker-compose
    colocar no ar (azure)
    escrever testes
     */

    @Autowired
    public HomeController(final ArticleService articleService, final SubscriberService subscriberService,
                          final VisitorService visitorService, final ImageService imageService) {
        this.articleService = articleService;
        this.subscriberService = subscriberService;
        this.visitorService = visitorService;
        this.imageService=imageService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) throws Exception {
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
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo, Model model) throws Exception {
        int pageSize = 5;
        Page<Article> page = articleService.findPaginated(pageNo, pageSize);
        visitorService.saveVisitor();
        List<Article> articleList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("articleList", articleList);
        LOGGER.info("Log - Paginated");
        return "index";
    }


    @PostMapping(value = "/subscribe")
    public ResponseEntity<Subscriber> subscribe(Subscriber subscriber) {
        subscriberService.addSubscriber(subscriber);
        LOGGER.info("Log - Subscriber: " + subscriber.getEmail());
        return ResponseEntity.ok().build();
    }


}