package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User getUser() {
        Optional<User> userOptional = repository.findById(1L);
        User user = new User();
        if(!userOptional.isPresent()) {
            return user;
        }
        user = repository.findById(1L).get();
        return user;
    }

    public User updateUser(User user) {
        return repository.save(user);
    }

    public void saveUser(User user) {
        Optional<User> userOptional = repository.findById(1L);
        if (userOptional.isPresent())
            repository.save(user);
    }
}
