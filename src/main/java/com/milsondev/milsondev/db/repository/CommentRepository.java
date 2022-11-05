package com.milsondev.milsondev.db.repository;

import com.milsondev.milsondev.db.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comment WHERE ARTICLE_ID =?1", nativeQuery = true)
    List<Comment> findAllByArticle_id(Long article_id);
}


