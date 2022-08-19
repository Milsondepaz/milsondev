package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.dtos.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    User save(UserDTO userDTO);
}
