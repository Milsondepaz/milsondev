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

    private String title;

    private String shortDescription;

    private String description;

    private String githubLink;

    private String gitLabLink;

    private String youtubeLink;

    private String liveLink;

    private Instant createdOn;

    private Instant updatedOn;

}
