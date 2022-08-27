package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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

        Article article2 = new Article();
        article2.setTitle("Fruit Shop (Java 8/Srping Boot + PostgreSQL + Thymeleaf");
        article2.setDescription("Desktop application, developed with Java 8, thats simulates");
        article2.setGithubLink("https://github.com/Milsondepaz/cinemofruitshop");
        article2.setYoutubeLink("https://youtu.be/er7pSuhFsmo");
        article2.setKeyWords("Key Words: #Java #JavaSE #Threads #Concurrency #DesignPatterns");
        article1.setShortName("#article02");



        Article article3 = new Article();
        article3.setTitle("Account and Billing Control System");
        article3.setDescription("Desktop application, developed with Java 8, thats simulates ");
        article3.setGithubLink("https://github.com/Milsondepaz/cobranca");
        article3.setYoutubeLink("https://youtu.be/EAmblRh06YM");
        article2.setKeyWords("Key Words: #Java #JavaSE #Threads #Concurrency #DesignPatterns");
        article1.setShortName("#article03");



        repository.save(article3);
        repository.save(article2);
        repository.save(article1);

    }

    public List<Article> getArticleList() {
        return repository.findAll();
    }

    public boolean saveArticle(Article articleDto){
        //Article article = repository.save(converter.convert(articleDto));
        //return converter.convert(article);
        return  false;
    }


}
