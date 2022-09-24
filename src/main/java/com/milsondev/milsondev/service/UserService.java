package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Optional<User> getUser() {
        return repository.findById(1L);
    }

    public User updateUser(User user) {
        return repository.save(user);
    }

    public void saveUser(User user) {
        repository.save(user);
    }
}
