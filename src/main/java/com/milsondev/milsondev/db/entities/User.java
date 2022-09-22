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

    @NotBlank(message = "E-mail is mandatory")
    @Email(message = "E-mail is not valid")
    private String email;

    //@NotBlank(message = "E-mail is mandatory")
    //@Size(min = 5, max=10, message = "User name should be between 5 and 10")

    private String userName = "milsona";

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Phone number is mandatory")
    private String phone;

    @NotBlank(message = "Git Hub link is mandatory")
    private String githubLink;

    @NotBlank(message = "Git Lab link is mandatory")
    private String gitlabLink;

    @NotBlank(message = "LinkedIn link is mandatory")
    private String linkedinLink;

    @NotBlank(message = "YouTube Channel link is mandatory")
    private String youtubeLink;

    //@NotBlank(message = "Instagram link is mandatory")
    private String instagramLink;

    private String firstParagraph;

    private String secondParagraph;

    private String thirdParagraph;


}
