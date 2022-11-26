package com.milsondev.milsondev.db.repository;

import com.milsondev.milsondev.db.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import javax.transaction.Transactional;


import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByFileName(String htmlPage);
       @Query(
            value = "SELECT * FROM article a where a.published = true \n-- #pageable\n",
            countQuery = "SELECT count(*) FROM article",
            nativeQuery = true)
    Page<Article> findAllCustom(Pageable pageable);

    //@Query(value = "SELECT * FROM article a where  a.title ilike %:searchedWord%", nativeQuery = true)
    //@Query(value = "SELECT * FROM article a where  a.title ilike %?1%", countQuery = "SELECT count(*) FROM article", nativeQuery = true)
    //@Query(value = "SELECT * FROM article a \n-- #pageable\n",   countQuery = "SELECT count(*) FROM article",          nativeQuery = true)


    @Query(value = "SELECT * FROM article a where  a.title ilike %?1%", nativeQuery = true)
    List<Article> findArticleCustom( String searchTerm);


}
