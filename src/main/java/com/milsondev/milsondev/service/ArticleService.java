package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.paging.Paged;
import com.milsondev.milsondev.db.entities.paging.Paging;
import com.milsondev.milsondev.db.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repository;


    @PostConstruct
    public void dataBase() {
        Article article1 = new Article();
        article1.setTitle("Simulator for Transport Management of Containerized Port Cargo");
        article1.setDescription("Desktop application, developed with Java 8, thats simulates ");
        article1.setGithubLink("https://github.com/Milsondepaz/tbagroup-milson");
        article1.setYoutubeLink("https://youtu.be/73tzcFTnhSI");
        article1.setKeyWords("Key Words: #Java #JavaSE #Threads #Concurrency #DesignPatterns");
        article1.setShortName("#article01");
        article1.setAuthor("milsondev");
        article1.setNumbersOfViews(0);


        Article article2 = new Article();
        article2.setTitle("Fruit Shop (Java 8/Srping Boot + PostgreSQL + Thymeleaf");
        article2.setDescription("Desktop application, developed with Java 8, thats simulates");
        article2.setGithubLink("https://github.com/Milsondepaz/cinemofruitshop");
        article2.setYoutubeLink("https://youtu.be/er7pSuhFsmo");
        article2.setKeyWords("Key Words: #Java #JavaSE #Threads #Concurrency #DesignPatterns");
        article2.setPage("ecommerce-fruit-shop-with-java-and-srping");
        article2.setShortName("#article02");
        article2.setAuthor("milsondev");
        article2.setNumbersOfViews(0);



        Article article3 = new Article();
        article3.setTitle("Account and Billing Control System");
        article3.setDescription("Desktop application, developed with Java 8, thats simulates ");
        article3.setGithubLink("https://github.com/Milsondepaz/cobranca");
        article3.setYoutubeLink("https://youtu.be/EAmblRh06YM");
        article3.setKeyWords("Key Words: #Java #JavaSE #Threads #Concurrency #DesignPatterns");
        article3.setPage("account-and-billing-control-system");
        article3.setShortName("#article03");
        article3.setAuthor("milsondev");
        article2.setNumbersOfViews(0);


        Article article4 = new Article();
        article4.setTitle("Javax Validation - Java & Spring Boot");
        article4.setDescription("Desktop application, developed with Java 8, thats simulates ");
        article4.setGithubLink("https://github.com/Milsondepaz/cobranca");
        article4.setYoutubeLink("https://youtu.be/EAmblRh06YM");
        article4.setKeyWords("Key Words: #Java #JavaSE #Threads #Concurrency #DesignPatterns");
        article4.setPage("java-spring-boot-validation-thymeleaf");
        article4.setShortName("#article04");
        article4.setAuthor("milsondev");
        article4.setNumbersOfViews(0);

        repository.save(article4);
        repository.save(article3);
        repository.save(article2);
        repository.save(article1);


    }

    public List<Article> getPageableArticleList() {
        Pageable firstPageWithTreeElements = PageRequest.of(0, 3);
        return repository.findAll(firstPageWithTreeElements).toList();
    }

    public List<Article> getArticleList() {
        return repository.findAll();
    }

    public void deleteArticleById(long id){
        repository.deleteById(id);
    }

    public void changeStateOfArticle (long id) {
        Article article = repository.findById(id).get();

        boolean valor = article.isEnable();

        if (article.isEnable() == false){
            article.setEnable(true);
        } else {
            article.setEnable(false);
        }

        repository.save(article);

        valor = article.isEnable();

    }




    public boolean saveArticle(Article articleDto){
        //Article article = repository.save(converter.convert(articleDto));
        //return converter.convert(article);
        return  false;
    }

    public Optional<Article> getArticleByHtmlPage(Long id) {
        return repository.findById(id);
    }

    public Optional<Article> getArticleByHtmlPage(String htmlPage) {
        return repository.findByPage(htmlPage);
    }

    public void articleUpdateNumbersOfViews(Article article) {
        repository.save(article);
    }

    public void updateArticle(Article article) {
        article.setCreatedUpdateOn(Instant.now());
        repository.save(article);
    }

    public Paged<Article> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        //Optional<Page<Article>> articlePage2 = repository.findAllPageCustomQuery(request);

        //Page<Article> articlePage = articlePage2.get();

        Page<Article> articlePage = repository.findAll(request);

        // ja tem q vir filtrado do banco


        return new Paged<>(articlePage, Paging.of(articlePage.getTotalPages(), pageNumber, size));
    }



}
