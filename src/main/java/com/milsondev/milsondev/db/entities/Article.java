package com.milsondev.milsondev.db.entities;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
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

    private String author = "milsona";

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Column(columnDefinition = "TEXT", length=300)
    //@Size(min = 239, max=300, message = "Description should be between 239 and 300")
    private String description;

    private String sourceCode;

    private String liveLink;

    private String tags;

    //private List<String> listTags = new ArrayList<>();

    @NotBlank(message = "File Name is mandatory")
    @Column(unique=true)
    public String fileName;

    private String path = "./articles/";

    private Instant createdUpdateOn = Instant.now();

    private boolean published = false;
    Random r = new Random();
    private int views = r.nextInt(25);
    private int likes =  r.nextInt(25);

    public String fortmadetData = getFormatedDate();

    private String getFormatedDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.getDefault() )
                .withZone( ZoneId.systemDefault() );
       return formatter.format(getCreatedUpdateOn());
    }



}
