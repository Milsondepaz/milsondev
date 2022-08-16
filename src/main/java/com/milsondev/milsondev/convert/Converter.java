package com.milsondev.milsondev.convert;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.dtos.ArticleDto;

import java.time.Instant;

public class Converter {

    public Article convert(ArticleDto articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setShortDescription(articleDto.getShortDescription());
        article.setDescription(articleDto.getDescription());
        article.setGithubLink(articleDto.getGithubLink());
        article.setGitLabLink (articleDto.getGitLabLink());
        article.setYoutubeLink(articleDto.getYoutubeLink());
        article.setLiveLink(articleDto.getLiveLink());
        article.setCreatedOn(Instant.now());
        return article;
    }

    public ArticleDto convert(Article article) {
        ArticleDto articleDto = new ArticleDto();
        return articleDto;
    }



}
