package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleService {

    private ArticleRepository repository;

    @Autowired
    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public boolean saveArticle(Article articleDto){
        //Article article = repository.save(converter.convert(articleDto));
        //return converter.convert(article);
        return  false;
    }


}
