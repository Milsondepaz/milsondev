package com.milsondev.milsondev.db.entities;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;
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

    @NotBlank(message = "Title is mandatory")
    @Column(name = "title",  unique = true, nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "short_desc",  unique = true, nullable = false, columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "description",  unique = true, nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "github_link", unique = true, columnDefinition = "TEXT")
    private String githubLink;

    @Column(name = "gitlab_link", unique = true, columnDefinition = "TEXT")
    private String gitLabLink;

    @Column(name = "youtube_link", unique = true, columnDefinition = "TEXT")
    private String youtubeLink;

    @Column(name = "live_link", unique = true, columnDefinition = "TEXT")
    private String liveLink;

    @Column(name = "created_on", updatable = false, nullable = false)
    private Instant createdOn;

    @Column(name = "updated_on")
    private Instant updatedOn;

}
