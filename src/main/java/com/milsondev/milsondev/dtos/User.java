package com.milsondev.milsondev.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NotBlank
@ToString
public class User {
    private String userName;
    private String password;
}
