package com.milsondev.milsondev.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NotBlank
@ToString
@NoArgsConstructor
public class UserDTO {
    private String userName;
    private String email;
    private String password;
}
