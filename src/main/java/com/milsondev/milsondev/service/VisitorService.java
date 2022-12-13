package com.milsondev.milsondev.service;

import com.milsondev.milsondev.db.entities.Visitor;
import com.milsondev.milsondev.db.repository.VisitorRepository;
import com.milsondev.milsondev.location.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Visitor> optionalVisitor = visitorRepository.findCustom(visitor.getIp(), visitor.formatData);
        if (!optionalVisitor.isPresent()){
            visitor.setCountVisitDay(1);
            visitorRepository.save(visitor);
        }else {
            Visitor visitorDB = optionalVisitor.get();
            visitorDB.setCountVisitDay(visitorDB.getCountVisitDay()+1);
            visitorRepository.save(visitorDB);
        }
    }

    public List<Visitor> getVisitorList() {
        return visitorRepository.findAll();
    }


}
