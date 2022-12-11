package com.milsondev.milsondev.service;

import com.milsondev.milsondev.controller.HomeController;
import com.milsondev.milsondev.db.entities.Subscriber;
import com.milsondev.milsondev.db.entities.Visitor;
import com.milsondev.milsondev.db.repository.VisitorRepository;
import com.milsondev.milsondev.location.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorService {
    private final VisitorRepository visitorRepository;
    private final LocationService locationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorService.class);


    @Autowired
    public VisitorService (final VisitorRepository visitorRepository, final LocationService locationService){
        this.visitorRepository = visitorRepository;
        this.locationService = locationService;
    }

    public void saveVisitor () throws Exception {
        Visitor visitor = locationService.getIPLocation();
        LOGGER.info("Log - Visitor " + visitor.toString());
        visitorRepository.save(visitor);
    }

    public List<Visitor> getVisitorList() {
        return visitorRepository.findAll();
    }


}
