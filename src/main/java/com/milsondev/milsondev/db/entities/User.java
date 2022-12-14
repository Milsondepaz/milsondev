package com.milsondev.milsondev.db.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "E-mail is not valid")
    private String email;

    @NotBlank(message = "User Name is mandatory")
    @Column(unique=true)
    private String userName;

    @NotBlank(message = "Password is mandatory")
    @Column(unique=true)
    private String password;

    private String repeatPassword;

    private String phone;

    private String githubLink;

    private String gitlabLink;

    private String linkedinLink;

    private String youtubeLink;

    private String instagramLink;

    private String introductionOneParagraph;

    private String moreAboutTreeParagraph;

    private String conclusionTreeParagraph;

}
