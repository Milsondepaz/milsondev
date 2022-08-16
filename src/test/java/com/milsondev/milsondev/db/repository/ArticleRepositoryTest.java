package com.milsondev.milsondev.db.repository;

import com.milsondev.milsondev.convert.Converter;
import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.dtos.ArticleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ArticleRepositoryTest {

    private ArticleRepository repository;
    private ArticleDto articleDto = new ArticleDto();
    private Converter converter = new Converter();

    @Autowired
    public ArticleRepositoryTest(ArticleRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    public void setup() {
        articleDto = new ArticleDto();
        articleDto.setTitle("Simulator for Transport Management of Containerized Port Cargo");
        articleDto.setShortDescription("Key Words: #Java #JavaSE #Threads #Concurrency #DesignPatterns");
        articleDto.setDescription("Desktop application, developed with Java 8, thats simulates the traffic and transport of containers " +
                "in a port environment. In the real world, these types of vehicles are mostly autonomous, only remotely controlled when necessary. " +
                "It was an assignment by the Düsseldorf-based company called TBA Groups.");
        articleDto.setGithubLink("https://github.com/Milsondepaz/tbagroup-milson");
        articleDto.setYoutubeLink("https://www.youtube.com/watch?v=73tzcFTnhSI&feature=youtu.be&ab_channel=MilsondePaz");
    }

    @DisplayName("JUnit test for save article operation")
    @Test
    public void givenArticleObject_whenSave_thenReturnSavedArticle(){
        Article savedArticle = repository.save(converter.convert(articleDto));
        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getId()).isGreaterThan(0);
    }





}