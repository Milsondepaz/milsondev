package com.milsondev.milsondev.db.repository;

import com.milsondev.milsondev.db.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {


}
