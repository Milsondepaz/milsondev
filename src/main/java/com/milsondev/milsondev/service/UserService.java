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

    @PostConstruct
    public void dataBase() {
        User user = new User();
        user.setEmail("milson.milson@outlook.com");
        user.setPhone("(+49) 1521 2816 680");
        user.setPassword("12345");

        user.setGithubLink("https://github.com/Milsondepaz?tab=repositories");
        user.setGitlabLink("https://gitlab.com/milsondev");
        user.setLinkedinLink("https://www.linkedin.com/in/milson-ant%C3%B3nio/");

        user.setInstagramLink("https://www.instagram.com/milsondev/");
        user.setYoutubeLink("https://www.youtube.com/channel/UCvW_xF7SdpAnpOo_Io-R9NA/videos");

        repository.save(user);

    }

    public Optional<User> getUser() {
        return repository.findById(1L);
    }

    public User updateUser(User user) {
        user.setId(1L);
        user.setUserName(user.getUserName().toLowerCase());
        return repository.save(user);
    }
}
