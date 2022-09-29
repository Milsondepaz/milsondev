package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.Emailing;
import com.milsondev.milsondev.db.entities.Subscriber;
import com.milsondev.milsondev.db.repository.EmailingRepository;
import com.milsondev.milsondev.db.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailingService {

    @Autowired
    EmailingRepository emailingRepository;

    @Autowired
    SubscriberRepository subscriberRepository;


    public void sendMails(Article article){
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        List<Emailing> emailingList = emailingRepository.findAll();


    }



}
