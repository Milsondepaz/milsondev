package com.milsondev.milsondev.convert;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.dtos.ArticleDto;

public class Converter {

    public Article convert(ArticleDto articleDto) {
        Article article = new Article();
        return article;
    }

    public ArticleDto convert(Article article) {
        ArticleDto articleDto = new ArticleDto();
        return articleDto;
    }



}
