package com.milsondev.milsondev.db.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "email is mandatory")
    @Column(name = "email",  unique = true, nullable = false, columnDefinition = "TEXT")
    private String email;

    @NotBlank(message = "user name is mandatory")
    @Column(name = "user_name",  unique = true, nullable = false, columnDefinition = "TEXT")
    private String userName;

    @NotBlank(message = "password is mandatory")
    @Column(name = "password",  unique = true, nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(name = "phone",  unique = true, nullable = false, columnDefinition = "TEXT")
    private String phone;

    @Column(name = "location",  unique = true, nullable = false, columnDefinition = "TEXT")
    private String location;

    @Column(name = "github_link", unique = true, columnDefinition = "TEXT")
    private String githubLink;

    @Column(name = "gitlab_link", unique = true, columnDefinition = "TEXT")
    private String gitLabLink;

    @Column(name = "linkedin_link", unique = true, columnDefinition = "TEXT")
    private String linkedinLink;

    @Column(name = "youtube_link", unique = true, columnDefinition = "TEXT")
    private String youtubeLink;

    @Column(name = "instagram_link", unique = true, columnDefinition = "TEXT")
    private String instagramLink;

    @Column(name = "about", unique = true, columnDefinition = "TEXT")
    private String about;

    @Column(name = "first_paragraph", unique = true, columnDefinition = "TEXT")
    private String firstParagraph;

    @Column(name = "second_paragraph", unique = true, columnDefinition = "TEXT")
    private String secondParagraph;

    @Column(name = "third_paragraph", unique = true, columnDefinition = "TEXT")
    private String thirdParagraph;

}
