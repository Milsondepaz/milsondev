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




import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {


    Optional<Article> findByPage(String htmlPage);

    //@Modifying
    //@Query(value = "select * from article where id not in ('22','10','5');", nativeQuery = true)
    //Optional<Page<Article>> findAllPageCustomQuery(Pageable pageable);
}
