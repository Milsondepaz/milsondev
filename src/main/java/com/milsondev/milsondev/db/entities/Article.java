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
    @Column(name = "title", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String title;

    @Column(name = "short_desc", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String shortDescription;

    @Column(name = "description", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String description;

    @Column(name = "github_link", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String githubLink;

    @Column(name = "gitlab_link", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String gitLabLink;

    @Column(name = "youtube_link", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String youtubeLink;

    @Column(name = "live_link", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String liveLink;

    @Column(name = "created_on", updatable = false, nullable = false)
    private Instant createdOn = Instant.now();

    @Column(name = "updated_on")
    private Instant updatedOn;

}
