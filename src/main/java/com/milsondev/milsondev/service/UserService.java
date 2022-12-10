package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	User save(User user);
}
