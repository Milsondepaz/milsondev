package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.Comment;
import com.milsondev.milsondev.db.entities.paging.Paged;
import com.milsondev.milsondev.db.entities.paging.Paging;
import com.milsondev.milsondev.db.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repository;

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
    }

    public void saveArticle(Article article) throws IOException {
        final String fileName = article.getTitle().toLowerCase().replaceAll(" ", "-");
        article.setFileName(fileName);
        article.setListTags(convertToTagList(article.getTags()));
        article.setUrl("https://www.milsondev.de/article/"+fileName);
        repository.save(article);
    }

    public List<String> convertToTagList(String tags) {
        String vetor [] = tags.split(";");
        return Arrays.asList(vetor);
    }

    public void updateArticleInfo(Article article){
        Article articleDB = repository.findById(article.getId()).get();
        articleDB.setTitle(article.getTitle());
        articleDB.setCreatedUpdateOn(Instant.now());
        articleDB.setDescription(article.getDescription());
        articleDB.setSourceCode(article.getSourceCode());
        articleDB.setLiveLink(article.getLiveLink());
        articleDB.setPath(article.getPath());
        articleDB.setYoutubeLink(article.getYoutubeLink());
        articleDB.setReadingTime(article.getReadingTime());
        repository.save(articleDB);
    }

    public Optional<Article> getArticleByFileName(String fileName) {
        return repository.findByFileName(fileName);
    }

    public String getArticleFileNameById(Long id) {
        Optional<Article> article = repository.findById(id);
        return article.get().getFileName();
    }

    public void articleUpdate(Article article) {
        repository.save(article);
    }

    public Paged<Article> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        Page<Article> articlePage = repository.findAllCustom(request);
        return new Paged<>(articlePage, Paging.of(articlePage.getTotalPages(), pageNumber, size));
    }

}