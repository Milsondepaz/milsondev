package com.milsondev.milsondev.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ArticleDto {

    private String title;

    private String shortDescription;

    private String description;

    private String githubLink;

    private String gitLabLink;

    private String youtubeLink;

    private String liveLink;

    private Instant createdOn = Instant.now();

    private Instant updatedOn;
}
