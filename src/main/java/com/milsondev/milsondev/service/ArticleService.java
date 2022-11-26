package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    public List<Article> getPageableArticleList(int pageNumber, int size) {
        Pageable firstPageWithTreeElements = PageRequest.of(pageNumber, size);
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

    /*
    public Paged<Article> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber-1, size-1);
        Page<Article> articlePage = repository.findAllCustom(request);
        return new Paged<>(articlePage, Paging.of(articlePage.getTotalPages(), pageNumber, size));
    }
     */

    public Page<Article> findPaginated(int pageNo, int pageSize) {
        final String fieldOne = "createdUpdateOn";
        Sort sort = Sort.by(fieldOne).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.repository.findAll(pageable);
    }

    public Page<Article> findPaginatedAndFilter(int pageNo, int pageSize, String searchedWord) {
        final String fieldOne = "createdUpdateOn";
        Sort sort = Sort.by(fieldOne).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        List<Article> articleList = repository.findArticleCustom(searchedWord);
        Page<Article> page = new PageImpl<>(articleList.subList(pageNo - 1, pageSize), pageable, articleList.size());
        return page;
    }



}