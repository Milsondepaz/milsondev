package com.milsondev.milsondev.db.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Data
@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    public Comment(String author, String review, Long article_id){
        this.author = author;
        this.review = review;
        this.data = Instant.now();
        this.article_id = article_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long article_id;

    @NotBlank(message = "Name is mandatory")
    private String author;

    @NotBlank(message = "Write a comment")
    private String review;
    private Instant data = Instant.now();

    private String formateDate = getCommentFormatedDate();

    private String getCommentFormatedDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.getDefault() )
                .withZone( ZoneId.systemDefault() );
        return formatter.format(getData());
    }

}
