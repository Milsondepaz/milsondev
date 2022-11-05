package com.milsondev.milsondev.service;


import com.milsondev.milsondev.db.entities.Comment;
import com.milsondev.milsondev.db.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommentService {

    private CommentRepository repository;

    @Autowired
    public CommentService ( CommentRepository repository){
        this.repository = repository;
    }

    public void saveComment(Comment comment){
        repository.save(comment);
    }

    public List<Comment> getCommentByArticle_Id(Long article_id){


       return repository.findAllByArticle_id(article_id);
    }

}
