package com.milsondev.milsondev.service;

import com.milsondev.milsondev.controller.HomeController;
import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.Subscriber;
import com.milsondev.milsondev.db.repository.SubscriberRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {

    private final SubscriberRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    public SubscriberService (final SubscriberRepository repository){
        this.repository = repository;
    }

    public void addSubscriber(Subscriber sub){
        Optional<Subscriber> subscriberDB =  repository.findAllByEmail(sub.getEmail());
        if (!subscriberDB.isPresent()){
            repository.save(sub);
        }
    }

    public List<Subscriber> getSubscriberList() {
        return repository.findAll();
    }



    public void exportToCSV(Writer writer) {
        List<Subscriber> SubscriberList = repository.findAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (Subscriber subscriber : SubscriberList) {
                csvPrinter.printRecord(subscriber.getId(), subscriber.getEmail(), subscriber.getFortmadetData());
            }
        } catch (IOException e) {
            LOGGER.error("Error While writing CSV ", e);
        }
    }

    public void exportToPDF() {
    }

    public void enableDisableSubscriber(long id) {
        Subscriber subscriber = repository.findById(id).get();
        if (subscriber.isActive() == false){
            subscriber.setActive(true);
        } else {
            subscriber.setActive(false);
        }
        repository.save(subscriber);
    }

    public void deleteSubscriber(long id) {
        repository.deleteById(id);
    }
}
