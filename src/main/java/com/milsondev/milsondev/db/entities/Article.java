package com.milsondev.milsondev.db.entities;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    @Column(name="id_author")
    private Long idAuthor;

    @Column(name="id_image")
    private Long idImage;

    //@NotBlank(message = "Title is mandatory")
    private String title;

    //@NotBlank(message = "Description is mandatory")
    @Column(columnDefinition = "TEXT", length=300)
    //@Size(min = 239, max=300, message = "Description should be between 239 and 300")
    private String description;

    private String sourceCode;

    private String liveLink;

    private String youtubeLink;

    @Column(name="listTags")
    @ElementCollection(targetClass=String.class)
    private List<String> listTags = new ArrayList<>();

    //@NotBlank(message = "File Name is mandatory")
    @Column(unique=true)
    public String fileName;

    public String tags;

    private String path = "./articles/";

    private String url;

    private Instant createdUpdateOn = Instant.now();

    private boolean published = false;
    private int views;
    private int likes;

    @Transient
    private MultipartFile multipartFile;

    @Transient
    private String articleCoverImageBase64;

    @Column(name="image_data_type", nullable = false)
    private String articleCoverImageDataType;

    @Column(name="image_original_file_name", nullable = false)
    private String articleCoverImageOriginalFileName;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column(name="image_data_byte", nullable = false)
    private byte[] articleCoverImageDataByte;

    private int readingTime;

    public String fortmadetData = getFormatedDate();

    public String fortmadetDataShort = getFormatedDateShort();

    private String getFormatedDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.getDefault() )
                .withZone( ZoneId.systemDefault() );
       return formatter.format(getCreatedUpdateOn());
    }

    private String getFormatedDateShort(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.getDefault() )
                .withZone( ZoneId.systemDefault() );
        return formatter.format(getCreatedUpdateOn()).substring(0, 10);
    }





}
