package com.milsondev.milsondev.db.repository;

import com.milsondev.milsondev.db.entities.Emailing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailingRepository  extends JpaRepository<Emailing, Long> {


}


