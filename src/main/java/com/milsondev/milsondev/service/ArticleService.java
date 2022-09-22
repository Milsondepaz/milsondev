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

        boolean valor = article.isPublished();

        if (article.isPublished() == false){
            article.setPublished(true);
        } else {
            article.setPublished(false);
        }

        repository.save(article);

        valor = article.isPublished();

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
        Page<Article> articlePage = repository.findAllCustom(request);
        return new Paged<>(articlePage, Paging.of(articlePage.getTotalPages(), pageNumber, size));
    }



}
