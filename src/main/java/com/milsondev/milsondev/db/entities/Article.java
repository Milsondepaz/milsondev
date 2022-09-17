package com.milsondev.milsondev.db.entities;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import javax.validation.constraints.*;



@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shortName;

    private String author;

    private String title;

    private String description;

    private String githubLink;

    private String youtubeLink;

    private String keyWords;

    private String page;

    private String path;

    private Instant createdUpdateOn = Instant.now();

    private int numbersOfViews;

    public String getFormatedDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.getDefault() )
                .withZone( ZoneId.systemDefault() );

        Instant instant = getCreatedUpdateOn();
       return formatter.format( instant );
    }



    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
