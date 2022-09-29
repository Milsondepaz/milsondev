package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.Subscriber;
import com.milsondev.milsondev.db.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {

    @Autowired
    SubscriberRepository repository;

    public void addSubscriber(Subscriber sub){
        Optional<Subscriber> subscriberDB =  repository.findAllByEmail(sub.getEmail());
        if (!subscriberDB.isPresent())
            repository.save(sub);
    }

    public List<Subscriber> getSubscriberList() {
        return repository.findAll();
    }
}
