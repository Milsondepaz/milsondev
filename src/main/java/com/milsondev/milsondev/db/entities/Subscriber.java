package com.milsondev.milsondev.db.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriber")
@ToString
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "E-mail is not valid")
    @NotBlank(message = "User Name is mandatory")
    @Column(unique=true)
    private String email;
}
