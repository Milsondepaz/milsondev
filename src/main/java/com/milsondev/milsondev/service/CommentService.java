package com.milsondev.milsondev.service;


import com.milsondev.milsondev.db.entities.Comment;
import com.milsondev.milsondev.db.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    public List<Comment> getCommentList(Long article_id){
        //Sort sort = Sort.by("dataCadastro").descending();
       return repository.findAllByArticle_id(article_id);
    }


    public List<Comment> getListWithLastComent(Long idArticle) {
        List<Comment> list = repository.findAllByArticle_id(idArticle);
        if (list.size() > 1){
            Comment coment = list.get(list.size() - 1);
            list.clear();
            list.add(coment);
        }
        return list;
    }
}
