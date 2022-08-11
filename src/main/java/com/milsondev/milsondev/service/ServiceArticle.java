package com.milsondev.milsondev.service;

import com.milsondev.milsondev.convert.Converter;
import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.repository.ArticleRepository;
import com.milsondev.milsondev.dtos.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceArticle {

    private ArticleRepository repository;
    private Converter converter;

    @Autowired
    public ServiceArticle(ArticleRepository repository, Converter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public ArticleDto saveArticle(ArticleDto articleDto){
        Article article = repository.save(converter.convert(articleDto));
        return converter.convert(article);
    }


}
