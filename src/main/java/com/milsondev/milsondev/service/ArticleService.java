package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.repository.ArticleRepository;
import com.milsondev.milsondev.exceptions.ArticleNotFoundException;
import com.milsondev.milsondev.util.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;


import java.io.*;
import java.time.Instant;
import java.util.*;


@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleService.class);

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

    public boolean changeStateOfArticle (long id) {
        Article article = repository.findById(id).get();
        boolean valor = article.isPublished();
        if (article.isPublished() == false){
            article.setPublished(true);
        } else {
            article.setPublished(false);
        }
        return repository.save(article).isPublished();
    }

    public void saveArticle(Article article) throws IOException {
        repository.save(articleSetAttributesBeforeSave(article));
    }

    private Article articleSetAttributesBeforeSave(Article article) throws IOException {

        final String fileName = article.getTitle().toLowerCase().replaceAll(" ", "-");
        article.setFileName(fileName);
        article.setListTags(convertToTagList(article.getTags()));
        article.setUrl("https://www.milsondev.de/article/"+fileName);

        article.setArticleCoverImageDataByte(ImageUtils.compressImage(article.getMultipartFile().getBytes()));
        article.setArticleCoverImageDataType(article.getMultipartFile().getContentType());
        article.setArticleCoverImageOriginalFileName(article.getMultipartFile().getOriginalFilename());

        return article;
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

    public Article getArticleByFileName(String fileName) {
        Optional<Article> optionalArticle = repository.findByFileName(fileName);

        if(optionalArticle.isEmpty()){
            LOGGER.error("Error not found");
        }

        return articleSetAttributesBeforeSendToController(optionalArticle.get());
    }

    private Article articleSetAttributesBeforeSendToController(Article article) {
        article.setArticleCoverImageBase64(convertByteToImageBase64(article));
        article.setMultipartFile(createMultipartFile(article));
        return article;
    }

    private String convertByteToImageBase64 (Article article){
       return "data:" + article.getArticleCoverImageDataType() + ";base64," + Base64.getEncoder().encodeToString(ImageUtils.decompressImage(article.getArticleCoverImageDataByte()));
    }

    private MultipartFile createMultipartFile(Article article){
        return new MockMultipartFile(article.getArticleCoverImageOriginalFileName(),
                article.getArticleCoverImageOriginalFileName(),
                article.getArticleCoverImageDataType(), article.getArticleCoverImageDataByte());
    }

    public String getArticleFileNameById(Long id) {
        Optional<Article> article = repository.findById(id);
        return article.get().getFileName();
    }

    public void articleIncrementView(Article article) {
        article.setViews(article.getViews() + 1);
        repository.save(article);
    }

    public Page<Article> findPaginated(int pageNo, int pageSize) {
        List<Article> articleList = repository.findAllPublishedTrue();
        Page<Article> page = new PageImpl<>(Collections.emptyList());
        if (!articleList.isEmpty()) {
            if (articleList.size() < pageSize) {
                pageSize = articleList.size();
            }
            final String fieldOne = "createdUpdateOn";
            Sort sort = Sort.by(fieldOne).descending();
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
            page = new PageImpl<>(articleList.subList(pageNo - 1, pageSize), pageable, articleList.size());
        }
        return page;
    }

    public Page<Article> findPaginatedAndFilter(int pageNo, int pageSize, String searchedWord) {
        final String fieldOne = "createdUpdateOn";
        Sort sort = Sort.by(fieldOne).descending();
        List<Article> articleList = repository.findArticleCustom(searchedWord);
        Page<Article> page = new PageImpl<>(Collections.emptyList());
        if (!articleList.isEmpty()) {
            if (articleList.size() < pageSize) {
                pageSize = articleList.size();
            }
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
            page = new PageImpl<>(articleList.subList(pageNo - 1, pageSize), pageable, articleList.size());
        }
        return page;
    }


    public Integer incrementLikes(Long idArticle)  {
        Article article = repository.findById(idArticle).orElseThrow(ArticleNotFoundException::new);
        article.setLikes(article.getLikes()+1);
        repository.save(article);
        LOGGER.info("Log - Article like: " + article.getTitle() + "number of likes "+ article.getLikes());
        return article.getLikes();
    }
}